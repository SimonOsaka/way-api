package com.zl.way.user.model;

import java.io.Serializable;
import java.util.Date;

public class UserFeedback implements Serializable {
    private Long id;

    private Byte feedbackOsType;

    private Byte feedbackType;

    private String feedbackAppVersion;

    private Date feedbackTime;

    private Long userLoginId;

    private Date createTime;

    private String feedbackContent;

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

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public Long getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {
        this.userLoginId = userLoginId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent == null ? null : feedbackContent.trim();
    }
}