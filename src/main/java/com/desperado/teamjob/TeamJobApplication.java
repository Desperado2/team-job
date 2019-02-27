package com.desperado.teamjob;

import com.battcn.swagger.annotation.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableSwagger2Doc
@SpringBootApplication
@EnableScheduling
public class TeamJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamJobApplication.class, args);
    }

}

