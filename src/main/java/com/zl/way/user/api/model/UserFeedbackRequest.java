package com.zl.way.user.api.model;

import java.io.Serializable;

import com.zl.way.util.PageParam;

public class UserFeedbackRequest extends PageParam implements Serializable {
    private Long id;

    private Byte feedbackOsType;

    private Byte feedbackType;

    private String feedbackAppVersion;

    private String feedbackContent;

    private Long userLoginId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getFeedbackOsType() {
        return feedbackOsType;
    }

    public void setFeedbackOsType(Byte feedbackOsType) {
        this.feedbackOsType = feedbackOsType;
    }

    public Byte getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Byte feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackAppVersion() {
        return feedbackAppVersion;
    }

    public void setFeedbackAppVersion(String feedbackAppVersion) {
        this.feedbackAppVersion = feedbackAppVersion == null ? null : feedbackAppVersion.trim();
    }

    public Long getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
}