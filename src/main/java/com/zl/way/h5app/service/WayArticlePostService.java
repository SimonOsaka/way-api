package com.zl.way.h5app.service;

import com.zl.way.h5app.service.model.WayGetArticlePostBo;
import com.zl.way.h5app.service.model.WayQueryArticlePostBo;
import com.zl.way.util.PageParam;

public interface WayArticlePostService {

    /**
     * 查询文章集合
     * 
     * @param keyword
     * @param pageParam
     * @return
     */
    WayQueryArticlePostBo queryArticlePost(String keyword, PageParam pageParam);

    /**
     * 获取指定{postId}的文章
     * 
     * @param postId
     * @return
     */
    WayGetArticlePostBo getArticlePost(Long postId);

}
