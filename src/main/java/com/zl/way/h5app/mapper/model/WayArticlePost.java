package com.zl.way.h5app.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class WayArticlePost implements Serializable {
    private Long id;

    private Long commodityId;

    private String subject;

    private Long postContentId;

    private Byte auditAction;

    private Byte isDeleted;

    private Date publishedTime;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public Long getPostContentId() {
        return postContentId;
    }

    public void setPostContentId(Long postContentId) {
        this.postContentId = postContentId;
    }

    public Byte getAuditAction() {
        return auditAction;
    }

    public void setAuditAction(Byte auditAction) {
        this.auditAction = auditAction;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}