package com.zl.way.sp.service.model;

/**
 * @author xuzhongliang
 */
public class WayCreateArticlePostParam {

    private Long commodityId;

    private String subject;

    private String postContent;

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
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

    public static final class WayCreateArticlePostBoBuilder {
        private WayCreateArticlePostParam wayCreateArticlePostBo;

        private WayCreateArticlePostBoBuilder() {
            wayCreateArticlePostBo = new WayCreateArticlePostParam();
        }

        public static WayCreateArticlePostBoBuilder aWayCreateArticlePostBo() {
            return new WayCreateArticlePostBoBuilder();
        }

        public WayCreateArticlePostBoBuilder withCommodityId(Long commodityId) {
            wayCreateArticlePostBo.setCommodityId(commodityId);
            return this;
        }

        public WayCreateArticlePostBoBuilder withSubject(String subject) {
            wayCreateArticlePostBo.setSubject(subject);
            return this;
        }

        public WayCreateArticlePostBoBuilder withPostContent(String postContent) {
            wayCreateArticlePostBo.setPostContent(postContent);
            return this;
        }

        public WayCreateArticlePostBoBuilder but() {
            return aWayCreateArticlePostBo().withCommodityId(wayCreateArticlePostBo.getCommodityId())
                .withSubject(wayCreateArticlePostBo.getSubject())
                .withPostContent(wayCreateArticlePostBo.getPostContent());
        }

        public WayCreateArticlePostParam build() {
            return wayCreateArticlePostBo;
        }
    }
}