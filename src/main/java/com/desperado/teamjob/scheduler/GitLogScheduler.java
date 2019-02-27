package com.desperado.teamjob.scheduler;

import com.desperado.teamjob.service.GitLogAnalysisService;
import com.desperado.teamjob.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GitLogScheduler {

    public static final Logger logger = LoggerFactory.getLogger(GitLogScheduler.class);
    @Autowired
    GitLogAnalysisService gitLogAnalysisService;

    @Scheduled(cron="* 31 14 * * ?")
    private void process(){
        logger.info(DateUtil.dateFmt(DateUtil.DEFAULT_DATETIME_PATTERN,new Date())+"-----统计git提交记录开始");
        gitLogAnalysisService.saveOrUpdate();
        logger.info(DateUtil.dateFmt(DateUtil.DEFAULT_DATETIME_PATTERN,new Date())+"-----统计git提交记录结束");
    }
}
