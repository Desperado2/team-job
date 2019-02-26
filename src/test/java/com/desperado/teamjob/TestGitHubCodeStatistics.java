package com.desperado.teamjob;

import com.desperado.teamjob.thread.GitHubCodeStatistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGitHubCodeStatistics {

    @Test
     public void test(){
        GitHubCodeStatistics gitHubCodeStatistics = new GitHubCodeStatistics();
        gitHubCodeStatistics.getUser();
    }

    @Test
    public void test1(){
        GitHubCodeStatistics gitHubCodeStatistics = new GitHubCodeStatistics();
        gitHubCodeStatistics.listBranch(true,"c:/code/testgit/GitTest");
    }

    @Test
    public void test3(){
        GitHubCodeStatistics gitHubCodeStatistics = new GitHubCodeStatistics();
        gitHubCodeStatistics.showCount("c:/code/testgit/GitTest","gitTest");
    }
}
