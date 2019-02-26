package com.desperado.teamjob.thread;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.desperado.teamjob.config.GitContentsConfig;
import com.desperado.teamjob.utils.HttpUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitHubCodeStatistics extends AbstractCodeStatistics {

   public static final  Logger logger = LoggerFactory.getLogger(GitHubCodeStatistics.class);

    @Autowired
    private GitContentsConfig gitContentsConfig;

    @Autowired
    private GitLogService gitLogService;

    @Override
    public void getUser() {
        String url="https://api.github.com/orgs/baicaicoder1/members";
        String token ="d79bbda6003cdcc8be2172f3952e932ad9871677";
        String result = null;
        try {
            result = HttpUtils.doGet(url+"?access_token="+token);
            JSONArray array = JSONArray.parseArray(result);
            for (int i = 0; i < array.size(); i++){
                JSONObject user = array.getJSONObject(i);
                String userName = user.getString("login");
                System.out.println(userName);
                getRepositoryByUser(userName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    @Override
    public void getRepositoryByUser(String username) {
        String url="https://api.github.com/users/"+username+"/repos";
        String token ="d79bbda6003cdcc8be2172f3952e932ad9871677";
        String result = null;
        try {
            result = HttpUtils.doGet(url+"?access_token="+token);
            JSONArray array = JSONArray.parseArray(result);
            for (int i = 0; i < array.size(); i++){
                JSONObject user = array.getJSONObject(i);
                System.out.println(user.getString("language"));
                System.out.println(user.getString("branches_url"));
                System.out.println(user.getString("svn_url"));
                System.out.println(user.getString("ssh_url"));
                System.out.println(user.getString("clone_url"));
                System.out.println(user.getString("name"));
                System.out.println(user.getString("created_at"));
                cloneWithAuthentication(user.getString("clone_url"),user.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

    @Override
    public void cloneWithAuthentication(String sshUrl,String repositoryName) throws IOException{
        File localPath = new File("c:/code/testgit/"+repositoryName);
        if (!localPath.exists()) {
            localPath.mkdirs();
        }
        try (Git repo = Git.cloneRepository().setURI(sshUrl).setDirectory(localPath).call()) {
            // then clone
            logger.info("Cloning from " + sshUrl + " to " + localPath);
            System.out.println( repo.getRepository().getDirectory().getAbsolutePath());
            showLogs(repo.getRepository().getDirectory().getAbsolutePath());
        } catch (Exception e) {
            // Note: the call() returns an opened repository already which needs
            // to be closed to avoid file handle leaks!
            logger.error("{}", e);
        }

    }

    @Override
    public List<Ref> listBranch(boolean isListAllBranch, String gitUrl) {
        List<Ref> refList = null;
        try {
            refList = GitOperation.listBranch(isListAllBranch, gitUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return refList;
    }

    @Override
    public void showLogs(String gitUrl) {
        try {
            GitOperation.showLogs(gitUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCount(String gitUrl,String name) {
        try {
            gitLogService.listAllLines(gitUrl,name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
