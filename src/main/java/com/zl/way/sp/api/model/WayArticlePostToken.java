package com.zl.way.sp.api.model;

import java.util.Objects;

import org.springframework.validation.annotation.Validated;

/**
 * WayArticlePostToken
 */
@Validated
public class WayArticlePostToken {
    private String token = null;

    public WayArticlePostToken token(String token) {
        this.token = token;
        return this;
    }

    /**
     * Get token
     * 
     * @return token
     **/

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayArticlePostToken wayArticlePostToken = (WayArticlePostToken)o;
        return Objects.equals(this.token, wayArticlePostToken.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WayArticlePostToken {\n");

        sb.append("    token: ").append(toIndentedString(token)).append("\n");
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
