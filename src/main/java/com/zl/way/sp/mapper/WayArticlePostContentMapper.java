package com.zl.way.sp.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.sp.model.WayArticlePostContent;

@Repository("spWayArticlePostContentMapper")
public interface WayArticlePostContentMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(WayArticlePostContent record);

    WayArticlePostContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePostContent record);

}