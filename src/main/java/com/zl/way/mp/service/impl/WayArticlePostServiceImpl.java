package com.zl.way.mp.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zl.way.mp.exception.NotExistException;
import com.zl.way.mp.mapper.WayArticlePostContentMapper;
import com.zl.way.mp.mapper.WayArticlePostLogMapper;
import com.zl.way.mp.mapper.WayArticlePostMapper;
import com.zl.way.mp.mapper.WayCommodityMapper;
import com.zl.way.mp.mapper.model.WayArticlePost;
import com.zl.way.mp.mapper.model.WayArticlePostContent;
import com.zl.way.mp.mapper.model.WayArticlePostLog;
import com.zl.way.mp.model.WayCommodity;
import com.zl.way.mp.model.WayCommodityParam;
import com.zl.way.mp.service.WayArticlePostService;
import com.zl.way.mp.service.model.WayAuditArticlePostParam;
import com.zl.way.mp.service.model.WayGetArticlePostBo;
import com.zl.way.mp.service.model.WayQueryArticlePostBo;
import com.zl.way.util.DateUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;

@Service("mpWayArticlePostService")
public class WayArticlePostServiceImpl implements WayArticlePostService {
    private final WayArticlePostMapper articlePostMapper;
    private final WayArticlePostContentMapper articlePostContentMapper;
    private final WayArticlePostLogMapper articlePostLogMapper;
    private final WayCommodityMapper commodityMapper;

    @Autowired
    public WayArticlePostServiceImpl(WayArticlePostMapper articlePostMapper,
        WayArticlePostContentMapper articlePostContentMapper, WayArticlePostLogMapper articlePostLogMapper,
        WayCommodityMapper commodityMapper) {
        this.articlePostMapper = articlePostMapper;
        this.articlePostContentMapper = articlePostContentMapper;
        this.articlePostLogMapper = articlePostLogMapper;
        this.commodityMapper = commodityMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void auditArticlePost(WayAuditArticlePostParam auditArticlePostParam) throws NotExistException {
        WayArticlePost dbArticlePost = articlePostMapper.selectByPrimaryKey(auditArticlePostParam.getPostId());
        if (null == dbArticlePost || dbArticlePost.getIsDeleted() == 1) {
            throw new NotExistException("文章不存在");
        }

        WayArticlePost articlePost = new WayArticlePost();
        articlePost.setId(auditArticlePostParam.getPostId());
        // 审核是否通过
        if (auditArticlePostParam.getPass()) {
            // 是：状态变为正常=已发布，动作为通过
            articlePost.setIsDeleted((byte)0);
            articlePost.setAuditAction((byte)2);
            articlePost.setPublishedTime(DateUtil.getCurrent());

            this.insertArticlePostLog(auditArticlePostParam.getPostId(), (byte)5, "审核通过文章主体");
        } else {
            // 否：状态变为编辑中（驳回、草稿），动作为驳回
            articlePost.setIsDeleted((byte)3);
            articlePost.setAuditAction((byte)3);

            this.insertArticlePostLog(auditArticlePostParam.getPostId(), (byte)6,
                "原因：" + auditArticlePostParam.getRejectContent());
        }
        articlePost.setUpdateTime(DateUtil.getCurrent());
        articlePostMapper.updateByPrimaryKeySelective(articlePost);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void deleteArticlePost(Long postId) throws NotExistException {
        WayArticlePost dbArticlePost = articlePostMapper.selectByPrimaryKey(postId);
        if (null == dbArticlePost || dbArticlePost.getIsDeleted() == 1) {
            throw new NotExistException("文章不存在");
        }

        WayArticlePost delArticlePost = new WayArticlePost();
        delArticlePost.setId(postId);
        delArticlePost.setIsDeleted((byte)1);
        articlePostMapper.updateByPrimaryKeySelective(delArticlePost);

        this.insertArticlePostLog(dbArticlePost.getId(), (byte)3, "删除文章主体");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayQueryArticlePostBo queryArticlePost(String keyword, Long postId, PageParam pageParam) {
        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayArticlePost> articlePostList = articlePostMapper.querySelective(keyword, postId, pageable);
        Integer articlePostTotal = articlePostMapper.querySelectiveTotal(keyword, postId, pageable);

        return new WayQueryArticlePostBo(Optional.ofNullable(articlePostList).orElse(Collections.emptyList()),
            articlePostTotal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayGetArticlePostBo getArticlePost(Long postId) {
        WayArticlePost dbArticlePost = articlePostMapper.selectByPrimaryKey(postId);
        WayGetArticlePostBo getArticlePostBo = new WayGetArticlePostBo();
        if (null == dbArticlePost || dbArticlePost.getIsDeleted() == 1) {
            return getArticlePostBo;
        }

        Long postContentId = dbArticlePost.getPostContentId();
        WayArticlePostContent dbArticlePostContent = articlePostContentMapper.selectByPrimaryKey(postContentId);
        getArticlePostBo.setArticlePost(dbArticlePost);
        getArticlePostBo.setArticlePostContent(dbArticlePostContent);

        WayCommodityParam commodityParam = new WayCommodityParam();
        commodityParam.setId(dbArticlePost.getCommodityId());
        WayCommodity commodity = commodityMapper.selectByPrimaryKey(dbArticlePost.getCommodityId());
        if (null != commodity) {
            getArticlePostBo.setCommodityName(commodity.getName());
        }
        return getArticlePostBo;
    }

    private void insertArticlePostLog(long articlePostId, byte eventType, String eventContent) {
        WayArticlePostLog articlePostLog = new WayArticlePostLog();
        articlePostLog.setArticlePostId(articlePostId);
        articlePostLog.setCreateTime(DateUtil.getCurrent());
        articlePostLog.setEventContent(eventContent);
        articlePostLog.setEventSource((byte)1);
        articlePostLog.setEventType(eventType);
        articlePostLogMapper.insertSelective(articlePostLog);
    }

}
