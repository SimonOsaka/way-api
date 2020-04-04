package com.zl.way.mp.service;

import com.zl.way.mp.exception.NotExistException;
import com.zl.way.mp.service.model.WayAuditArticlePostParam;
import com.zl.way.mp.service.model.WayGetArticlePostBo;
import com.zl.way.mp.service.model.WayQueryArticlePostBo;
import com.zl.way.util.PageParam;

public interface WayArticlePostService {

    /**
     * 审核文章
     *
     * @param auditArticlePostParam
     */
    void auditArticlePost(WayAuditArticlePostParam auditArticlePostParam) throws NotExistException;

    /**
     * 删除文章
     * 
     * @param postId
     */
    void deleteArticlePost(Long postId) throws NotExistException;

    /**
     * 查询文章集合
     * 
     * @param keyword
     * @param postId
     * @param pageParam
     * @return
     */
    WayQueryArticlePostBo queryArticlePost(String keyword, Long postId, PageParam pageParam);

    /**
     * 获取指定{postId}的文章
     * 
     * @param postId
     * @return
     */
    WayGetArticlePostBo getArticlePost(Long postId);

}
