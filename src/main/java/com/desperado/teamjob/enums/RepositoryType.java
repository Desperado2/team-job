package com.desperado.teamjob.enums;

public enum  RepositoryType {
    SVN(0),
    GIT(1);
    private int code;

    RepositoryType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
