package com.zl.way.sp.service.model;

import java.util.List;

import com.zl.way.sp.model.WayArticlePost;

public class WayQueryArticlePostBo {
    private List<WayArticlePost> articlePostList;

    public WayQueryArticlePostBo(List<WayArticlePost> articlePostList) {
        this.articlePostList = articlePostList;
    }

    public List<WayArticlePost> getArticlePostList() {
        return articlePostList;
    }

    public void setArticlePostList(List<WayArticlePost> articlePostList) {
        this.articlePostList = articlePostList;
    }
}
