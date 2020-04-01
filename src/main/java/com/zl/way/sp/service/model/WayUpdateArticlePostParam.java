package com.zl.way.sp.service.model;

/**
 * @author xuzhongliang
 */
public class WayUpdateArticlePostParam {

    private Long postId;

    private String subject;

    private String postContent;

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

    public static final class WayUpdateArticlePostBoBuilder {
        private WayUpdateArticlePostParam wayUpdateArticlePostBo;

        private WayUpdateArticlePostBoBuilder() {
            wayUpdateArticlePostBo = new WayUpdateArticlePostParam();
        }

        public static WayUpdateArticlePostBoBuilder aWayUpdateArticlePostBo() {
            return new WayUpdateArticlePostBoBuilder();
        }

        public WayUpdateArticlePostBoBuilder withPostId(Long postId) {
            wayUpdateArticlePostBo.setPostId(postId);
            return this;
        }

        public WayUpdateArticlePostBoBuilder withSubject(String subject) {
            wayUpdateArticlePostBo.setSubject(subject);
            return this;
        }

        public WayUpdateArticlePostBoBuilder withPostContent(String postContent) {
            wayUpdateArticlePostBo.setPostContent(postContent);
            return this;
        }

        public WayUpdateArticlePostBoBuilder but() {
            return aWayUpdateArticlePostBo().withPostId(wayUpdateArticlePostBo.getPostId())
                .withSubject(wayUpdateArticlePostBo.getSubject())
                .withPostContent(wayUpdateArticlePostBo.getPostContent());
        }

        public WayUpdateArticlePostParam build() {
            return wayUpdateArticlePostBo;
        }
    }
}