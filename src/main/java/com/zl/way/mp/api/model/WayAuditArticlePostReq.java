package com.zl.way.mp.api.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

/**
 * WayAuditArticlePostReq
 */
@Validated
public class WayAuditArticlePostReq {
    private Integer pass = 0;

    private String rejectContent = null;

    public WayAuditArticlePostReq pass(Integer pass) {
        this.pass = pass;
        return this;
    }

    /**
     * Get pass
     * 
     * @return pass
     **/
    @NotNull

    public Integer getPass() {
        return pass;
    }

    public void setPass(Integer pass) {
        this.pass = pass;
    }

    public WayAuditArticlePostReq rejectContent(String rejectContent) {
        this.rejectContent = rejectContent;
        return this;
    }

    /**
     * Get rejectContent
     * 
     * @return rejectContent
     **/

    public String getRejectContent() {
        return rejectContent;
    }

    public void setRejectContent(String rejectContent) {
        this.rejectContent = rejectContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayAuditArticlePostReq wayAuditArticlePostReq = (WayAuditArticlePostReq)o;
        return Objects.equals(this.pass, wayAuditArticlePostReq.pass)
            && Objects.equals(this.rejectContent, wayAuditArticlePostReq.rejectContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pass, rejectContent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WayAuditArticlePostReq {\n");

        sb.append("    pass: ").append(toIndentedString(pass)).append("\n");
        sb.append("    rejectContent: ").append(toIndentedString(rejectContent)).append("\n");
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
