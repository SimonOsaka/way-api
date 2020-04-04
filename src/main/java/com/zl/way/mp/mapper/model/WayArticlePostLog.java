package com.zl.way.mp.mapper.model;

import java.io.Serializable;
import java.util.Date;

public class WayArticlePostLog implements Serializable {
    private Long id;

    private Long articlePostId;

    private Byte eventType;

    private String eventContent;

    private Byte eventSource;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticlePostId() {
        return articlePostId;
    }

    public void setArticlePostId(Long articlePostId) {
        this.articlePostId = articlePostId;
    }

    public Byte getEventType() {
        return eventType;
    }

    public void setEventType(Byte eventType) {
        this.eventType = eventType;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent == null ? null : eventContent.trim();
    }

    public Byte getEventSource() {
        return eventSource;
    }

    public void setEventSource(Byte eventSource) {
        this.eventSource = eventSource;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}