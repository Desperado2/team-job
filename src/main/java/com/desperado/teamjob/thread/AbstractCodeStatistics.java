package com.desperado.teamjob.thread;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;

import java.io.IOException;
import java.util.List;

public abstract class AbstractCodeStatistics {

    //1.获取小组用户
    public void getUser(){}

    //2.获取用户的仓库列表
    public void getRepositoryByUser(String username){}

    //3.克隆仓库
    public void cloneWithAuthentication(String sshUrl,String repositoryName) throws IOException {}

    //4.获取所有分支

    public abstract List<Ref> listBranch(boolean isListAllBranch, String gitUrl) throws IOException, GitAPIException;

    //5.统计log
    public  void showLogs(String gitUrl){}
}
