package com.zl.way.sp.service;

import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.exception.NotExistException;
import com.zl.way.sp.service.model.*;
import com.zl.way.util.PageParam;

public interface WayArticlePostService {
    /**
     * 创建文章
     * 
     * @param createArticlePostParam
     */
    void createArticlePostAndContent(WayCreateArticlePostParam createArticlePostParam) throws BusinessException;

    /**
     * 修改文章
     * 
     * @param updateArticlePostParam
     */
    void updateArticlePostAndContent(WayUpdateArticlePostParam updateArticlePostParam)
        throws NotExistException, BusinessException;

    /**
     * 修改文章
     *
     * @param updateArticlePostParam
     */
    void updateAndSubmitArticlePostAndContent(WayUpdateArticlePostParam updateArticlePostParam)
        throws NotExistException, BusinessException;

    /**
     * 删除文章
     * 
     * @param deleteArticlePostParam
     */
    void deleteArticlePost(WayDeleteArticlePostParam deleteArticlePostParam) throws NotExistException;

    /**
     * 查询文章集合
     * 
     * @param queryArticlePostParam
     * @return
     */
    WayQueryArticlePostBo queryArticlePost(WayQueryArticlePostParam queryArticlePostParam, PageParam pageParam);

    /**
     * 获取指定{postId}的文章
     * 
     * @param postId
     * @return
     */
    WayGetArticlePostBo getArticlePost(Long postId);

    /**
     * 获取驳回内容
     *
     * @param postId
     * @return
     */
    String getArticlePostRejectContent(Long postId);
}
