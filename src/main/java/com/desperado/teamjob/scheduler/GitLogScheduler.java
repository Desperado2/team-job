package com.desperado.teamjob.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GitLogScheduler {

    private int count=0;

    @Scheduled(cron="* * 22 * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }
}
