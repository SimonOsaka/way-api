package com.zl.way.sp.service.model;

/**
 * @author xuzhongliang
 */
public class WayUpdateArticlePostParam {

    private Long postId;

    private String subject;

    private String postContent;

    private Long commodityId;

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public static final class WayUpdateArticlePostParamBuilder {
        private WayUpdateArticlePostParam wayUpdateArticlePostParam;

        private WayUpdateArticlePostParamBuilder() {
            wayUpdateArticlePostParam = new WayUpdateArticlePostParam();
        }

        public static WayUpdateArticlePostParamBuilder aWayUpdateArticlePostParam() {
            return new WayUpdateArticlePostParamBuilder();
        }

        public WayUpdateArticlePostParamBuilder withPostId(Long postId) {
            wayUpdateArticlePostParam.setPostId(postId);
            return this;
        }

        public WayUpdateArticlePostParamBuilder withSubject(String subject) {
            wayUpdateArticlePostParam.setSubject(subject);
            return this;
        }

        public WayUpdateArticlePostParamBuilder withPostContent(String postContent) {
            wayUpdateArticlePostParam.setPostContent(postContent);
            return this;
        }

        public WayUpdateArticlePostParamBuilder withCommodityId(Long commodityId) {
            wayUpdateArticlePostParam.setCommodityId(commodityId);
            return this;
        }

        public WayUpdateArticlePostParamBuilder but() {
            return aWayUpdateArticlePostParam().withPostId(wayUpdateArticlePostParam.getPostId())
                .withSubject(wayUpdateArticlePostParam.getSubject())
                .withPostContent(wayUpdateArticlePostParam.getPostContent())
                .withCommodityId(wayUpdateArticlePostParam.getCommodityId());
        }

        public WayUpdateArticlePostParam build() {
            return wayUpdateArticlePostParam;
        }
    }
}