package com.zl.way.mp.service.model;

public class WayAuditArticlePostParam {
    private Long postId;
    private Boolean pass;
    private String rejectContent;

    public String getRejectContent() {
        return rejectContent;
    }

    public void setRejectContent(String rejectContent) {
        this.rejectContent = rejectContent;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }

    public static final class WayAuditArticlePostParamBuilder {
        private WayAuditArticlePostParam wayAuditArticlePostParam;

        private WayAuditArticlePostParamBuilder() {
            wayAuditArticlePostParam = new WayAuditArticlePostParam();
        }

        public static WayAuditArticlePostParamBuilder aWayAuditArticlePostParam() {
            return new WayAuditArticlePostParamBuilder();
        }

        public WayAuditArticlePostParamBuilder withPostId(Long postId) {
            wayAuditArticlePostParam.setPostId(postId);
            return this;
        }

        public WayAuditArticlePostParamBuilder withPass(Boolean pass) {
            wayAuditArticlePostParam.setPass(pass);
            return this;
        }

        public WayAuditArticlePostParamBuilder withRejectContent(String rejectContent) {
            wayAuditArticlePostParam.setRejectContent(rejectContent);
            return this;
        }

        public WayAuditArticlePostParamBuilder but() {
            return aWayAuditArticlePostParam().withPostId(wayAuditArticlePostParam.getPostId())
                .withPass(wayAuditArticlePostParam.getPass())
                .withRejectContent(wayAuditArticlePostParam.getRejectContent());
        }

        public WayAuditArticlePostParam build() {
            return wayAuditArticlePostParam;
        }
    }
}
