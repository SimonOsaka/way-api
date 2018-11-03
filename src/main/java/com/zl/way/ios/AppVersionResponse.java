package com.zl.way.ios;

import java.util.List;

public class AppVersionResponse {

    private String operation;

    private String newAppVersion;

    private List<String> commentList;

    private String appStoreUrl;

    public String getOperation() {

        return operation;
    }

    public void setOperation(String operation) {

        this.operation = operation;
    }

    public String getNewAppVersion() {

        return newAppVersion;
    }

    public void setNewAppVersion(String newAppVersion) {

        this.newAppVersion = newAppVersion;
    }

    public List<String> getCommentList() {

        return commentList;
    }

    public void setCommentList(List<String> commentList) {

        this.commentList = commentList;
    }

    public String getAppStoreUrl() {

        return appStoreUrl;
    }

    public void setAppStoreUrl(String appStoreUrl) {

        this.appStoreUrl = appStoreUrl;
    }
}
