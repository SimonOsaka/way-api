package com.zl.way.sp.service.model;

import com.zl.way.sp.model.WayArticlePost;
import com.zl.way.sp.model.WayArticlePostContent;

public class WayGetArticlePostBo {
    private WayArticlePost articlePost;
    private WayArticlePostContent articlePostContent;
    private String commodityName;

    public WayArticlePost getArticlePost() {
        return articlePost;
    }

    public void setArticlePost(WayArticlePost articlePost) {
        this.articlePost = articlePost;
    }

    public WayArticlePostContent getArticlePostContent() {
        return articlePostContent;
    }

    public void setArticlePostContent(WayArticlePostContent articlePostContent) {
        this.articlePostContent = articlePostContent;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }
}
