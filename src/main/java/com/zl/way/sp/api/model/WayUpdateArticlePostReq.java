package com.zl.way.sp.api.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * WayUpdateArticlePostReq
 */
@Validated
public class WayUpdateArticlePostReq {
    private String subject = null;

    private Long commodityId = null;

    private String postContent = null;

    private Integer enableSubmit = 0;

    public WayUpdateArticlePostReq subject(String subject) {
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

    public WayUpdateArticlePostReq commodityId(Long commodityId) {
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

    public WayUpdateArticlePostReq postContent(String postContent) {
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

    public WayUpdateArticlePostReq enableSubmit(Integer enableSubmit) {
        this.enableSubmit = enableSubmit;
        return this;
    }

    /**
     * Get enableSubmit
     * 
     * @return enableSubmit
     **/
    @NotNull

    public Integer getEnableSubmit() {
        return enableSubmit;
    }

    public void setEnableSubmit(Integer enableSubmit) {
        this.enableSubmit = enableSubmit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayUpdateArticlePostReq wayUpdateArticlePostReq = (WayUpdateArticlePostReq)o;
        return Objects.equals(this.subject, wayUpdateArticlePostReq.subject)
            && Objects.equals(this.commodityId, wayUpdateArticlePostReq.commodityId)
            && Objects.equals(this.postContent, wayUpdateArticlePostReq.postContent)
            && Objects.equals(this.enableSubmit, wayUpdateArticlePostReq.enableSubmit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, commodityId, postContent, enableSubmit);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WayUpdateArticlePostReq {\n");

        sb.append("    subject: ").append(toIndentedString(subject)).append("\n");
        sb.append("    commodityId: ").append(toIndentedString(commodityId)).append("\n");
        sb.append("    postContent: ").append(toIndentedString(postContent)).append("\n");
        sb.append("    enableSubmit: ").append(toIndentedString(enableSubmit)).append("\n");
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
