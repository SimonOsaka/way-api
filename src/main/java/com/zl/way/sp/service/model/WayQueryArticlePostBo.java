package com.zl.way.sp.service.model;

import java.util.List;

import com.zl.way.sp.model.WayArticlePost;

public class WayQueryArticlePostBo {
    private List<WayArticlePost> articlePostList;
    private Integer articlePostListTotal;

    public Integer getArticlePostListTotal() {
        return articlePostListTotal;
    }

    public void setArticlePostListTotal(Integer articlePostListTotal) {
        this.articlePostListTotal = articlePostListTotal;
    }

    public WayQueryArticlePostBo(List<WayArticlePost> articlePostList, Integer articlePostListTotal) {
        this.articlePostList = articlePostList;
        this.articlePostListTotal = articlePostListTotal;
    }

    public List<WayArticlePost> getArticlePostList() {
        return articlePostList;
    }

    public void setArticlePostList(List<WayArticlePost> articlePostList) {
        this.articlePostList = articlePostList;
    }
}
