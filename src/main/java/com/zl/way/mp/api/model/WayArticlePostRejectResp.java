package com.zl.way.mp.api.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

/**
 * WayArticlePostRejectResp
 */
@Validated
public class WayArticlePostRejectResp {
    private String rejectContent = null;

    public WayArticlePostRejectResp rejectContent(String rejectContent) {
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
        WayArticlePostRejectResp wayArticlePostRejectResp = (WayArticlePostRejectResp)o;
        return Objects.equals(this.rejectContent, wayArticlePostRejectResp.rejectContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rejectContent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WayArticlePostRejectResp {\n");

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
