package com.zl.way.sp.api.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * WayCreateArticlePostReq
 */
@Validated
public class WayCreateArticlePostReq {
    private String subject = null;

    private String postContent = null;

    private Long commodityId = null;

    private String postToken = null;

    public WayCreateArticlePostReq subject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Get subject
     * 
     * @return subject
     **/
    @NotNull

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public WayCreateArticlePostReq postContent(String postContent) {
        this.postContent = postContent;
        return this;
    }

    /**
     * Get postContent
     * 
     * @return postContent
     **/
    @NotNull

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public WayCreateArticlePostReq commodityId(Long commodityId) {
        this.commodityId = commodityId;
        return this;
    }

    /**
     * Get commodityId
     * 
     * @return commodityId
     **/
    @NotNull()

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public WayCreateArticlePostReq postToken(String postToken) {
        this.postToken = postToken;
        return this;
    }

    /**
     * Get postToken
     * 
     * @return postToken
     **/
    @NotNull

    public String getPostToken() {
        return postToken;
    }

    public void setPostToken(String postToken) {
        this.postToken = postToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayCreateArticlePostReq wayCreateArticlePostReq = (WayCreateArticlePostReq)o;
        return Objects.equals(this.subject, wayCreateArticlePostReq.subject)
            && Objects.equals(this.postContent, wayCreateArticlePostReq.postContent)
            && Objects.equals(this.commodityId, wayCreateArticlePostReq.commodityId)
            && Objects.equals(this.postToken, wayCreateArticlePostReq.postToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, postContent, commodityId, postToken);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WayCreateArticlePostReq {\n");

        sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("    postContent: ").append(toIndentedString(postContent)).append("\n");
        sb.append("    commodityId: ").append(toIndentedString(commodityId)).append("\n");
        sb.append("    postToken: ").append(toIndentedString(postToken)).append("\n");
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
