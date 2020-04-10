package com.zl.way.h5app.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zl.way.commodity.mapper.WayCommodityMapper;
import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.model.WayCommodityParam;
import com.zl.way.h5app.mapper.WayArticlePostContentMapper;
import com.zl.way.h5app.mapper.WayArticlePostMapper;
import com.zl.way.h5app.mapper.model.WayArticlePost;
import com.zl.way.h5app.mapper.model.WayArticlePostContent;
import com.zl.way.h5app.service.WayArticlePostService;
import com.zl.way.h5app.service.model.WayGetArticlePostBo;
import com.zl.way.h5app.service.model.WayQueryArticlePostBo;
import com.zl.way.util.PageParam;
import com.zl.way.util.WayPageRequest;

@Service
public class WayArticlePostServiceImpl implements WayArticlePostService {
    private final WayArticlePostMapper articlePostMapper;
    private final WayArticlePostContentMapper articlePostContentMapper;
    private final WayCommodityMapper commodityMapper;

    @Autowired
    public WayArticlePostServiceImpl(WayArticlePostMapper articlePostMapper,
        WayArticlePostContentMapper articlePostContentMapper, WayCommodityMapper commodityMapper) {
        this.articlePostMapper = articlePostMapper;
        this.articlePostContentMapper = articlePostContentMapper;
        this.commodityMapper = commodityMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public WayQueryArticlePostBo queryArticlePost(String keyword, PageParam pageParam) {
        Pageable pageable = WayPageRequest.of(pageParam);
        List<WayArticlePost> articlePostList = articlePostMapper.querySelective(keyword, pageable);
        Integer articlePostTotal = articlePostMapper.querySelectiveTotal(keyword, pageable);

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
            getArticlePostBo.setCommodityImage(commodity.getImgUrl());
        }
        return getArticlePostBo;
    }

}
