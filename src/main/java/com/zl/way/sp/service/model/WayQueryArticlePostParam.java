package com.zl.way.sp.service.model;

public class WayQueryArticlePostParam {
    private String keywords;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public static final class WayQueryArticlePostParamBuilder {
        private WayQueryArticlePostParam wayQueryArticlePostParam;

        private WayQueryArticlePostParamBuilder() {
            wayQueryArticlePostParam = new WayQueryArticlePostParam();
        }

        public static WayQueryArticlePostParamBuilder aWayQueryArticlePostParam() {
            return new WayQueryArticlePostParamBuilder();
        }

        public WayQueryArticlePostParamBuilder withKeywords(String keywords) {
            wayQueryArticlePostParam.setKeywords(keywords);
            return this;
        }

        public WayQueryArticlePostParamBuilder but() {
            return aWayQueryArticlePostParam().withKeywords(wayQueryArticlePostParam.getKeywords());
        }

        public WayQueryArticlePostParam build() {
            return wayQueryArticlePostParam;
        }
    }
}
