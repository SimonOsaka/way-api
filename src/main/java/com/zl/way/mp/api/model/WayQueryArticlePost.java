package com.zl.way.mp.api.model;

import java.util.Date;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

/**
 * WayQueryArticlePost
 */
@Validated
public class WayQueryArticlePost {
    private Long postId = null;

    private Long commodityId = null;

    private String commodityName = null;

    private String subject = null;

    private String postContent = null;

    private Date publishTime = null;

    private Integer postStatus = null;

    public WayQueryArticlePost postId(Long postId) {
        this.postId = postId;
        return this;
    }

    /**
     * Get postId
     * 
     * @return postId
     **/

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public WayQueryArticlePost commodityId(Long commodityId) {
        this.commodityId = commodityId;
        return this;
    }

    /**
     * Get commodityId
     * 
     * @return commodityId
     **/

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public WayQueryArticlePost commodityName(String commodityName) {
        this.commodityName = commodityName;
        return this;
    }

    /**
     * Get commodityName
     * 
     * @return commodityName
     **/

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public WayQueryArticlePost subject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Get subject
     * 
     * @return subject
     **/

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public WayQueryArticlePost postContent(String postContent) {
        this.postContent = postContent;
        return this;
    }

    /**
     * Get postContent
     * 
     * @return postContent
     **/

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public WayQueryArticlePost publishTime(Date publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    /**
     * Get publishTime
     * 
     * @return publishTime
     **/

    @Valid
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public WayQueryArticlePost postStatus(Integer postStatus) {
        this.postStatus = postStatus;
        return this;
    }

    /**
     * Get postStatus
     * 
     * @return postStatus
     **/

    public Integer getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(Integer postStatus) {
        this.postStatus = postStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayQueryArticlePost wayQueryArticlePost = (WayQueryArticlePost)o;
        return Objects.equals(this.postId, wayQueryArticlePost.postId)
            && Objects.equals(this.commodityId, wayQueryArticlePost.commodityId)
            && Objects.equals(this.commodityName, wayQueryArticlePost.commodityName)
            && Objects.equals(this.subject, wayQueryArticlePost.subject)
            && Objects.equals(this.postContent, wayQueryArticlePost.postContent)
            && Objects.equals(this.publishTime, wayQueryArticlePost.publishTime)
            && Objects.equals(this.postStatus, wayQueryArticlePost.postStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, commodityId, commodityName, subject, postContent, publishTime, postStatus);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WayQueryArticlePost {\n");

        sb.append("    postId: ").append(toIndentedString(postId)).append("\n");
        sb.append("    commodityId: ").append(toIndentedString(commodityId)).append("\n");
        sb.append("    commodityName: ").append(toIndentedString(commodityName)).append("\n");
        sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("    postContent: ").append(toIndentedString(postContent)).append("\n");
        sb.append("    publishTime: ").append(toIndentedString(publishTime)).append("\n");
        sb.append("    postStatus: ").append(toIndentedString(postStatus)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
