package com.desperado.teamjob.thread;

import com.desperado.teamjob.config.SvnContensConfig;
import com.desperado.teamjob.domain.GitCommitLogs;
import com.desperado.teamjob.domain.Project;
import com.desperado.teamjob.utils.DateUtil;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class SvnOperation {

    static boolean readonly = true;
    private static DefaultSVNOptions options = SVNWCUtil.createDefaultOptions( readonly );
    private static Random random = new Random();
    private static SVNRepository repos;
    private static ISVNAuthenticationManager authManager;



    public static  void init(Project project,SvnContensConfig svnContensConfig) throws SVNException{
        ISVNAuthenticationManager authManager =
                SVNWCUtil.createDefaultAuthenticationManager(new File(svnContensConfig.getTargetPath()+"/auth"),
                        svnContensConfig.getSvnUserName(), svnContensConfig.getSvnPassWord().toCharArray());
        options.setDiffCommand("-x -w");
        repos = SVNRepositoryFactory.create(SVNURL
                .parseURIEncoded(project.getRepositoryUrl()));
        repos.setAuthenticationManager(authManager);
        System.out.println("init completed");
    }

    /**获取一段时间内，所有的commit记录
     * @param st	开始时间
     * @param et	结束时间
     * @return
     * @throws SVNException
     */
    public static SVNLogEntry[] getLogByTime(Date st, Date et) throws SVNException{
        long startRevision = repos.getDatedRevision(st);
        long endRevision = repos.getDatedRevision(et);

        @SuppressWarnings("unchecked")
        Collection<SVNLogEntry> logEntries = repos.log(new String[]{""}, null,
                startRevision, endRevision, true, true);
        SVNLogEntry[] svnLogEntries = logEntries.toArray(new SVNLogEntry[0]);
        return svnLogEntries;
    }

    /**获取版本比较日志，并存入临时文件
     * @param startVersion
     * @param endVersion
     * @return
     * @throws SVNException
     * @throws IOException
     */
    public static File getChangeLog(Project project,SvnContensConfig svnContensConfig,long startVersion, long endVersion) throws SVNException, IOException{
        SVNDiffClient diffClient = new SVNDiffClient(authManager, options);
        diffClient.setGitDiffFormat(true);
        File tempLogFile = null;
        OutputStream outputStream = null;
        String svnDiffFile = null;
        do {
            svnDiffFile = svnContensConfig.getTargetPath() + "/svn_diff_file_"+startVersion+"_"+endVersion+"_"+random.nextInt(10000)+".txt";
            tempLogFile = new File(svnDiffFile);
        } while (tempLogFile != null && tempLogFile.exists());
        try {
            tempLogFile.createNewFile();
            outputStream = new FileOutputStream(svnDiffFile);
            diffClient.doDiff(SVNURL.parseURIEncoded(project.getRepositoryUrl()),
                    SVNRevision.create(startVersion),
                    SVNURL.parseURIEncoded(project.getRepositoryUrl()),
                    SVNRevision.create(endVersion),
                    org.tmatesoft.svn.core.SVNDepth.UNKNOWN, true, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null)
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return tempLogFile;
    }

    /**分析变更的代码，统计代码增量
     * @param file
     * @return
     * @throws Exception
     */
    public static Map<String, Integer> staticticsCodeAdd(File file) throws Exception{
        System.out.println("开始统计修改代码行数");
        FileReader fileReader = new FileReader(file);
        BufferedReader in = new BufferedReader(fileReader);
        int addLines = 0;
        int delLines = 0;
        String line = null;
        StringBuffer buffer = new StringBuffer(1024);
        boolean start = false;
        while((line=in.readLine()) != null){
            if(line.startsWith("Index:")){
                if(start){
                    ChangeFile changeFile = parseChangeFile(buffer);
                    Map<String, Integer> oneSize = staticOneFileChange(changeFile);
                    System.out.println("filePath="+changeFile.getFilePath()+"  changeType="+changeFile.getChangeType()+"  addLines="+oneSize);
                    addLines += oneSize.get("add");
                    delLines += oneSize.get("del");
                    buffer.setLength(0);
                }
                start = true;
            }
            buffer.append(line).append('\n');
        }
        if(buffer.length() > 0){
            ChangeFile changeFile = parseChangeFile(buffer);
            Map<String, Integer> oneSize = staticOneFileChange(changeFile);
            System.out.println("filePath="+changeFile.getFilePath()+"  changeType="+changeFile.getChangeType()+"  addLines="+oneSize);
            addLines += oneSize.get("add");
            delLines += oneSize.get("del");
        }
        in.close();
        fileReader.close();
        boolean deleteFile = file.delete();
        System.out.println("-----delete file-----"+deleteFile);
        Map<String,Integer> map = new HashMap<>();
        map.put("add",addLines);
        map.put("del",delLines);
        return map;
    }

    /**统计单个文件的增加行数，（先通过过滤器，如文件后缀、文件路径等等），也可根据修改类型来统计等，这里只统计增加或者修改的文件
     * @param changeFile
     * @return
     */
    public static Map<String,Integer> staticOneFileChange(ChangeFile changeFile){
        char changeType = changeFile.getChangeType();
        if(changeType == 'A'){
            return countAddAndDelLine(changeFile.getFileContent());
        }else if(changeType == 'M'){
            return countAddAndDelLine(changeFile.getFileContent());
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("add",0);
        map.put("del",0);
        return map;
    }



    /**解析单个文件变更日志
     * @param str
     * @return
     */
    public static ChangeFile parseChangeFile(StringBuffer str){
        int index = str.indexOf("\n@@");
        if(index > 0){
            String header = str.substring(0, index);
            String[] headers = header.split("\n");
            String filePath = headers[0].substring(7);
            char changeType = 'U';
            boolean oldExist = !headers[2].endsWith("(nonexistent)");
            boolean newExist = !headers[3].endsWith("(nonexistent)");
            if(oldExist && !newExist){
                changeType = 'D';
            }else if(!oldExist && newExist){
                changeType = 'A';
            }else if(oldExist && newExist){
                changeType = 'M';
            }
            int bodyIndex = str.indexOf("@@\n")+3;
            String body = str.substring(bodyIndex);
            ChangeFile changeFile = new ChangeFile(filePath, changeType, body);
            return changeFile;
        }else{
            String[] headers = str.toString().split("\n");
            String filePath = headers[0].substring(7);
            ChangeFile changeFile = new ChangeFile(filePath, 'U', null);
            return changeFile;
        }
    }

    /**通过比较日志，统计以+号开头的非空行
     * @param content
     * @return
     */
    public static Map<String,Integer> countAddAndDelLine(String content){
        int addLines = 0;
        int delLines = 0;
        if(content !=null){
            content = '\n' + content +'\n';
            char[] chars = content.toCharArray();
            int len = chars.length;
            //判断当前行是否以+号开头
            boolean startPlus = false;
            //判断当前行是否以-号开头
            boolean startSub = false;
            //判断当前行，是否为空行（忽略第一个字符为加号）
            boolean notSpace = false;

            for(int i=0;i<len;i++){
                char ch = chars[i];
                if(ch =='\n'){
                    //当当前行是+号开头，同时其它字符都不为空，则行数+1
                    if(startPlus && notSpace){
                        addLines++;
                        notSpace = false;
                    }
                    if(startSub && notSpace){
                        delLines++;
                        notSpace = false;
                    }
                    //为下一行做准备，判断下一行是否以+头
                    if(i < len-1 && chars[i+1] == '+'){
                        startPlus = true;
                        //跳过下一个字符判断，因为已经判断了
                        i++;
                    }else{
                        startPlus = false;
                        if(i < len-1 && chars[i+1] == '-'){
                            startSub = true;
                            i++;
                        }else{
                            startSub=false ;
                        }
                    }
                }else if((startPlus || startSub) && ch > ' '){//如果当前行以+、-开头才进行非空行判断
                    notSpace = true;
                }
            }
        }
        Map<String,Integer> map = new HashMap<>();
        map.put("add",addLines);
        map.put("del",delLines);
        return map;
    }

    /**统计一段时间内代码增加量
     * @param st
     * @param et
     * @return
     * @throws Exception
     */
    public static List<GitCommitLogs> staticticsCodeAddByTime(Project project,SvnContensConfig svnContensConfig, Date st, Date et) throws Exception{
        int addLines = 0;
        int delLines = 0;
        init(project,svnContensConfig);
        SVNLogEntry[] logs = getLogByTime(st, et);
        List<GitCommitLogs> logsList = new ArrayList<>();
        if(logs.length > 0){
            GitCommitLogs gitCommitLogs = null;
            long lastVersion = logs[0].getRevision()-1;
            for(SVNLogEntry log:logs){
                gitCommitLogs = new GitCommitLogs();
                File logFile = getChangeLog(project,svnContensConfig,lastVersion, log.getRevision());
                Map<String, Integer> lines = staticticsCodeAdd(logFile);
                Map<String, SVNPropertyValue> propertyValueMap = log.getRevisionProperties().asMap();
                gitCommitLogs.setId(UUID.randomUUID().toString());
                gitCommitLogs.setTotalAddLines(lines.get("add"));
                gitCommitLogs.setTotalDelLines(lines.get("del"));
                gitCommitLogs.setCommitType(1);
                if(propertyValueMap.containsKey("svn:log")){
                    gitCommitLogs.setCommitComment(propertyValueMap.get("svn:log").getString());
                }
                if(propertyValueMap.containsKey("svn:date")){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
                    Date date = sdf.parse(propertyValueMap.get("svn:date").getString());
                    date = DateUtil.addHours(date, 8);
                    gitCommitLogs.setDateCommit(date);
                }
                if(propertyValueMap.containsKey("svn:author")){
                    gitCommitLogs.setAuthor(propertyValueMap.get("svn:author").getString());
                }
                gitCommitLogs.setCommitId(gitCommitLogs.getAuthor()+"-"+gitCommitLogs.getDateCommit().getTime());
                lastVersion = log.getRevision();
                logsList.add(gitCommitLogs);
            }
        }
        return logsList;
    }

    /**获取某一版本有变动的文件路径
     * @param version
     * @return
     * @throws SVNException
     */
    public List<SVNLogEntryPath> getChangeFileList(Project project,SvnContensConfig svnContensConfig,long version) throws SVNException{

        List<SVNLogEntryPath> result = new ArrayList<>();
        SVNLogClient logClient = new SVNLogClient( authManager, options );
        SVNURL url = SVNURL.parseURIEncoded(project.getRepositoryUrl());
        String[] paths = { "." };
        SVNRevision pegRevision = SVNRevision.create( version );
        SVNRevision startRevision = SVNRevision.create( version );
        SVNRevision endRevision = SVNRevision.create( version );
        boolean stopOnCopy = false;
        boolean discoverChangedPaths = true;
        long limit = 9999l;

        ISVNLogEntryHandler handler = new ISVNLogEntryHandler() {

            /**
             * This method will process when doLog() is done
             */
            @Override
            public void handleLogEntry( SVNLogEntry logEntry ) throws SVNException {
                System.out.println( "Author: " + logEntry.getAuthor() );
                System.out.println( "Date: " + logEntry.getDate() );
                System.out.println( "Message: " + logEntry.getMessage() );
                System.out.println( "Revision: " + logEntry.getRevision() );
                System.out.println("-------------------------");
                Map<String, SVNLogEntryPath> maps = logEntry.getChangedPaths();
                Set<Map.Entry<String, SVNLogEntryPath>> entries = maps.entrySet();
                for(Map.Entry<String, SVNLogEntryPath> entry : entries){
                    //System.out.println(entry.getKey());
                    SVNLogEntryPath entryPath = entry.getValue();
                    result.add(entryPath);
                    System.out.println(entryPath.getType()+" "+entryPath.getPath());
                }
            }
        };
        // Do log
        try {
            logClient.doLog( url, paths, pegRevision, startRevision, endRevision, stopOnCopy, discoverChangedPaths, limit, handler );
        }
        catch ( SVNException e ) {
            System.out.println( "Error in doLog() " );
            e.printStackTrace();
        }
        return result;
    }

    /**获取指定文件内容
     * @param url	svn地址
     * @return
     */
    public String checkoutFileToString(String url){//"", -1, null
        try {
            SVNDirEntry entry = repos.getDir("", -1, false, null);
            int size = (int)entry.getSize();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(size);
            SVNProperties properties = new SVNProperties();
            repos.getFile("", -1, properties, outputStream);
            String doc = new String(outputStream.toByteArray(),Charset.forName("utf-8"));
            return doc;
        } catch (SVNException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**列出指定SVN 地址目录下的子目录
     * @param url
     * @return
     * @throws SVNException
     */
    public List<SVNDirEntry> listFolder(String url){
        if(checkPath(url)==1){

            try {
                Collection<SVNDirEntry> list = repos.getDir("", -1, null, (List<SVNDirEntry>)null);
                List<SVNDirEntry> dirs = new ArrayList<SVNDirEntry>(list.size());
                dirs.addAll(list);
                return dirs;
            } catch (SVNException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**检查路径是否存在
     * @param url
     * @return 1：存在    0：不存在   -1：出错
     */
    public int checkPath(String url){
        SVNNodeKind nodeKind;
        try {
            nodeKind = repos.checkPath("", -1);
            boolean result = nodeKind == SVNNodeKind.NONE ? false : true;
            if(result) return 1;
        } catch (SVNException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

}
