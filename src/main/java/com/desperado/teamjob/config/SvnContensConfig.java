package com.desperado.teamjob.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "svn")
public class SvnContensConfig {
    private String targetPath;
    private String svnUserName;
    private String svnPassWord;

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public String getSvnUserName() {
        return svnUserName;
    }

    public void setSvnUserName(String svnUserName) {
        this.svnUserName = svnUserName;
    }

    public String getSvnPassWord() {
        return svnPassWord;
    }

    public void setSvnPassWord(String svnPassWord) {
        this.svnPassWord = svnPassWord;
    }
}
