package com.zl.way.sp.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.exception.NotExistException;
import com.zl.way.sp.mapper.WayArticlePostContentMapper;
import com.zl.way.sp.mapper.WayArticlePostLogMapper;
import com.zl.way.sp.mapper.WayArticlePostMapper;
import com.zl.way.sp.mapper.model.WayArticlePostQuery;
import com.zl.way.sp.model.*;
import com.zl.way.sp.service.WayArticlePostService;
import com.zl.way.sp.service.WayCommodityService;
import com.zl.way.sp.service.model.*;
import com.zl.way.util.DateUtil;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;

@Service("spWayArticlePostService")
public class WayArticlePostServiceImpl implements WayArticlePostService {
    private final WayArticlePostMapper articlePostMapper;
    private final WayArticlePostContentMapper articlePostContentMapper;
    private final WayArticlePostLogMapper articlePostLogMapper;
    private final WayCommodityService commodityService;

    @Autowired
    public WayArticlePostServiceImpl(WayArticlePostMapper articlePostMapper,
        WayArticlePostContentMapper articlePostContentMapper, WayArticlePostLogMapper articlePostLogMapper,
        WayCommodityService commodityService) {
        this.articlePostMapper = articlePostMapper;
        this.articlePostContentMapper = articlePostContentMapper;
        this.articlePostLogMapper = articlePostLogMapper;
        this.commodityService = commodityService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void createArticlePostAndContent(WayCreateArticlePostParam createArticlePostBo) throws BusinessException {
        if (StringUtils.length(createArticlePostBo.getSubject()) > 20
            || StringUtils.isBlank(createArticlePostBo.getSubject())) {
            throw new BusinessException("文章主题必须在20字以内");
        }
        if (StringUtils.length(createArticlePostBo.getPostContent()) > 800
            || StringUtils.isBlank(createArticlePostBo.getPostContent())) {
            throw new BusinessException("文章内容必须在800字以内");
        }
        WayArticlePostContent articlePostContent = new WayArticlePostContent();
        articlePostContent.setContent(createArticlePostBo.getPostContent());
        articlePostContent.setCreateTime(DateUtil.getCurrent());
        articlePostContentMapper.insertSelective(articlePostContent);

        WayArticlePost articlePost = new WayArticlePost();
        articlePost.setPostContentId(articlePostContent.getId());
        articlePost.setCommodityId(createArticlePostBo.getCommodityId());
        articlePost.setCreateTime(DateUtil.getCurrent());
        articlePost.setIsDeleted((byte)2);
        articlePost.setSubject(createArticlePostBo.getSubject());
        articlePost.setAuditAction((byte)0);
        articlePostMapper.insertSelective(articlePost);

        this.insertArticlePostLog(articlePost.getId(), (byte)1, "创建文章主体");
        this.insertArticlePostLog(articlePost.getId(), (byte)4, "提交文章主体");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void updateArticlePostAndContent(WayUpdateArticlePostParam updateArticlePostBo)
        throws NotExistException, BusinessException {
        this.updateOrSubmitArticlePost(updateArticlePostBo, false);
        this.insertArticlePostLog(updateArticlePostBo.getPostId(), (byte)2, "更新文章主体");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void updateAndSubmitArticlePostAndContent(WayUpdateArticlePostParam updateArticlePostParam)
        throws NotExistException, BusinessException {
        this.updateOrSubmitArticlePost(updateArticlePostParam, true);
        this.insertArticlePostLog(updateArticlePostParam.getPostId(), (byte)4, "更新并提交文章主体");
    }

    private void updateOrSubmitArticlePost(WayUpdateArticlePostParam updateArticlePostBo, boolean enableSubmit)
        throws NotExistException, BusinessException {
        WayArticlePost dbArticlePost = articlePostMapper.selectByPrimaryKey(updateArticlePostBo.getPostId());
        if (null == dbArticlePost || dbArticlePost.getIsDeleted() == 1) {
            throw new NotExistException("文章不存在");
        }
        if (StringUtils.length(updateArticlePostBo.getSubject()) > 20
            || StringUtils.isBlank(updateArticlePostBo.getSubject())) {
            throw new BusinessException("文章主题必须在20字以内");
        }
        if (StringUtils.length(updateArticlePostBo.getPostContent()) > 800
            || StringUtils.isBlank(updateArticlePostBo.getPostContent())) {
            throw new BusinessException("文章内容必须在800字以内");
        }

        Long articlePostContentId = dbArticlePost.getPostContentId();

        WayArticlePostContent articlePostContent = new WayArticlePostContent();
        articlePostContent.setId(articlePostContentId);
        articlePostContent.setContent(updateArticlePostBo.getPostContent());
        articlePostContent.setUpdateTime(DateUtil.getCurrent());
        articlePostContentMapper.updateByPrimaryKeySelective(articlePostContent);

        WayArticlePost articlePost = new WayArticlePost();
        articlePost.setId(updateArticlePostBo.getPostId());
        // 是否可以提交
        if (enableSubmit) {
            // 是：状态变为提交中
            articlePost.setIsDeleted((byte)2);
            articlePost.setAuditAction((byte)1);
        }
        articlePost.setSubject(updateArticlePostBo.getSubject());
        articlePost.setCommodityId(updateArticlePostBo.getCommodityId());
        articlePost.setUpdateTime(DateUtil.getCurrent());
        articlePostMapper.updateByPrimaryKeySelective(articlePost);

    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void deleteArticlePost(WayDeleteArticlePostParam deleteArticlePostBo) throws NotExistException {
        WayArticlePost dbArticlePost = articlePostMapper.selectByPrimaryKey(deleteArticlePostBo.getPostId());
        if (null == dbArticlePost || dbArticlePost.getIsDeleted() == 1) {
            throw new NotExistException("文章不存在");
        }

        WayArticlePost delArticlePost = new WayArticlePost();
        delArticlePost.setId(deleteArticlePostBo.getPostId());
        delArticlePost.setIsDeleted((byte)1);
        articlePostMapper.updateByPrimaryKeySelective(delArticlePost);

        this.insertArticlePostLog(dbArticlePost.getId(), (byte)3, "删除文章主体");
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayQueryArticlePostBo queryArticlePost(WayQueryArticlePostParam queryArticlePostParam, PageParam pageParam) {
        WayArticlePostQuery query = new WayArticlePostQuery();
        query.setSubject(queryArticlePostParam.getKeywords());
        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayArticlePost> articlePostList = articlePostMapper.querySelective(query, pageable);
        Integer articlePostTotal = articlePostMapper.querySelectiveTotal(query, pageable);

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
        WayCommodityBo commodityBo = commodityService.getCommodity(commodityParam);
        if (null != commodityBo) {
            getArticlePostBo.setCommodityName(commodityBo.getName());
        }
        return getArticlePostBo;
    }

    private void insertArticlePostLog(long articlePostId, byte eventType, String eventContent) {
        WayArticlePostLog articlePostLog = new WayArticlePostLog();
        articlePostLog.setArticlePostId(articlePostId);
        articlePostLog.setCreateTime(DateUtil.getCurrent());
        articlePostLog.setEventContent(eventContent);
        articlePostLog.setEventSource((byte)0);
        articlePostLog.setEventType(eventType);
        articlePostLogMapper.insertSelective(articlePostLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public String getArticlePostRejectContent(Long postId) {
        List<WayArticlePostLog> articlePostLogList = articlePostLogMapper.queryArticlePostLog(postId, (byte)6);
        return CollectionUtils.isNotEmpty(articlePostLogList) ? articlePostLogList.get(0).getEventContent()
            : StringUtils.EMPTY;
    }
}
