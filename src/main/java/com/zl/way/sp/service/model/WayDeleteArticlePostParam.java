package com.zl.way.sp.service.model;

/**
 * @author xuzhongliang
 */
public class WayDeleteArticlePostParam {

    private Long postId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public static final class WayDeleteArticlePostBoBuilder {
        private WayDeleteArticlePostParam wayDeleteArticlePostBo;

        private WayDeleteArticlePostBoBuilder() {
            wayDeleteArticlePostBo = new WayDeleteArticlePostParam();
        }

        public static WayDeleteArticlePostBoBuilder aWayDeleteArticlePostBo() {
            return new WayDeleteArticlePostBoBuilder();
        }

        public WayDeleteArticlePostBoBuilder withPostId(Long postId) {
            wayDeleteArticlePostBo.setPostId(postId);
            return this;
        }

        public WayDeleteArticlePostBoBuilder but() {
            return aWayDeleteArticlePostBo().withPostId(wayDeleteArticlePostBo.getPostId());
        }

        public WayDeleteArticlePostParam build() {
            return wayDeleteArticlePostBo;
        }
    }
}