package com.desperado.teamjob.service;

import com.desperado.teamjob.dao.ProjectTemplateDao;
import com.desperado.teamjob.dao.UserDao;
import com.desperado.teamjob.domain.ProjectTemplate;
import com.desperado.teamjob.dto.ProjectTempDateLine;
import com.desperado.teamjob.dto.UserDto;
import com.desperado.teamjob.utils.DateUtil;
import com.desperado.teamjob.utils.IdGenerator;
import com.desperado.teamjob.utils.StringUtil;
import com.desperado.teamjob.utils.UserUtils;
import com.desperado.teamjob.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("projectTemplateService")
public class ProjectTemplateServiceImpl implements ProjectTemplateService {

    @Autowired
    private ProjectTemplateDao projectTemplateDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Result addOrUpdate(ProjectTemplate projectTemplate) {
        Result result = new Result();
        if(projectTemplate ==  null){
            return null;
        }
        if(StringUtil.isEmpty(projectTemplate.getId())){
            String id = getId();
            Date date = new Date();
            projectTemplate.setId(id);
            projectTemplate.setCreateDate(date);
            projectTemplate.setUpdateDate(date);
            projectTemplate.setOwner(UserUtils.getUser().getId());
            projectTemplateDao.add(projectTemplate);
            result.setData(projectTemplate);
        }else{
            Date date = new Date();
            projectTemplate.setUpdateDate(date);
            projectTemplateDao.update(projectTemplate);
            result.setData(projectTemplate);
        }
        return result;
    }

    @Override
    public Result getAllProjectDate() {
        Result result = new Result();
        List<ProjectTemplate> projectDate = projectTemplateDao.getAllProjectDate();
        dealTemplate(projectDate);
        result.setData(projectDate);
        return result;
    }

    @Override
    public Result getProjectDateById(String id) {
        Result result = new Result();
        ProjectTemplate projectDate = projectTemplateDao.getProjectDateById(id);
        result.setData(projectDate);
        return result;
    }

    @Override
    public Result getDetailById(String id) {
        ProjectTemplate template = projectTemplateDao.getProjectDateById(id);
        Map<Date, String> map = getLines(template);
        Map<Date, String> mapByKey = sortMapByKey(map);
        List<ProjectTempDateLine> lines = dealDateLines(mapByKey);
        Map<String,Object> resultMap = new HashMap<>();
        Result result = new Result();
        resultMap.put("remark",template.getRemark());
        resultMap.put("name",template.getProjectName());
        resultMap.put("dateLines",lines);
        result.setData(resultMap);
        return result;
    }


    private  List<ProjectTempDateLine> dealDateLines(Map<Date, String> mapByKey){
        List<ProjectTempDateLine> lines = new ArrayList<>();
        ProjectTempDateLine tempDateLine = null;
        Map<Integer,String> map = new TreeMap<>();
        int currWeek = DateUtil.getWeekOfYear(new Date());
        for (Date date : mapByKey.keySet()){
            int week = DateUtil.getWeekOfYear(date) - currWeek;
            if(map.containsKey(week)){
                String content = mapByKey.get(date);
                map.put(week,content +" "+ mapByKey.get(date));
            }else{
                map.put(week,mapByKey.get(date));
            }
        }
        tempDateLine = new ProjectTempDateLine();
        if(map.containsKey(1)){
            tempDateLine.setContent("下周工作:"+map.get(1));
        }else{
            tempDateLine.setContent("下周工作:");
        }
        tempDateLine.setColor("#0bbd87");
        tempDateLine.setTimestamp(getDateString(currWeek+1));
        lines.add(tempDateLine);

        tempDateLine = new ProjectTempDateLine();
        if(map.containsKey(0)){
            tempDateLine.setContent("本周工作:"+map.get(0));
        }else{
            tempDateLine.setContent("本周工作:");
        }
        tempDateLine.setTimestamp(getDateString(currWeek));
        lines.add(tempDateLine);

        tempDateLine = new ProjectTempDateLine();
        if(map.containsKey(-1)){
            tempDateLine.setContent("上周工作:"+map.get(-1));
        }else {
            tempDateLine.setContent("上周工作:");
        }
        tempDateLine.setTimestamp(getDateString(currWeek-1));
        lines.add(tempDateLine);

        int i =0;
        for (Integer week : map.keySet()){
            if(week < -1){
               i++;
            }
        }
        for (Integer week : map.keySet()){
            if(week < -1){
                tempDateLine = new ProjectTempDateLine();
                tempDateLine.setContent("第"+i+"周工作:"+map.get(week));
                i--;
                tempDateLine.setTimestamp(getDateString(currWeek+week));
                lines.add(tempDateLine);
            }
        }
        return lines;
    }


    /**
     * 获取id
     * @return
     */
    private String getId(){
        String id = IdGenerator.generate(8);
        ProjectTemplate template = projectTemplateDao.getProjectDateById(id);
        while (template != null){
            id = IdGenerator.generate(8);
            template = projectTemplateDao.getProjectDateById(id);
        }
        return id;
    }

    private void dealTemplate(List<ProjectTemplate> projectDate){
        //List<ProjectTemplate> templates = new ArrayList<>();
        for (ProjectTemplate template : projectDate){
            String owner = template.getOwner();
            UserDto user = userDao.selectUserById(owner);
            if(user != null){
                template.setOwner(user.getName());
            }
            String[] userIds = template.getGroupMembers().split(",");
            List<UserDto> list = userDao.selectUserByIds(Arrays.asList(userIds));
            StringBuilder sb = new StringBuilder();
            for (UserDto userDto : list){
                sb.append(userDto.getName());
                sb.append(",");
            }
            template.setGroupMembers(sb.toString().substring(0,sb.length()-1));
        }
    }


    private  Map<Date,String> getLines(ProjectTemplate templates){
        Map<Date,String> map = new HashMap<>();
        map.put(templates.getInterfaceReview(),"接口评审");
        map.put(templates.getCaseReview(),"用例评审");
        map.put(templates.getInterfaceTest(),"接口测试");
        map.put(templates.getAllTest(),"整体测试");
        map.put(templates.getPreDate(),"预发");
        map.put(templates.getProduceDate(),"上线");
        return map;
    }
    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    private  Map<Date, String> sortMapByKey(Map<Date, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Date, String> sortMap = new TreeMap<Date, String>(
                new Comparator<Date>() {
                    @Override
                    public int compare(Date date1, Date date2) {
                        return date1.compareTo(date2);
                    }
                });

        sortMap.putAll(map);
        return sortMap;
    }


    private String getDateString(int week){
        int year = DateUtil.getYear(new Date());
        Date firstDayOfWeek = DateUtil.getFirstDayOfWeek(year, week);
        Date lastDayOfWeek = DateUtil.getLastDayOfWeek(year, week);
        return DateUtil.dateFmt(DateUtil.DEFAULT_DATE_PATTERN,firstDayOfWeek)+" -- "+ DateUtil.dateFmt(DateUtil.DEFAULT_DATE_PATTERN,lastDayOfWeek);
    }

}


