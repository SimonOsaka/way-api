package com.zl.way.mp.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.mp.mapper.model.WayArticlePostContent;

@Repository("mpWayArticlePostContentMapper")
public interface WayArticlePostContentMapper {

    int insertSelective(WayArticlePostContent record);

    WayArticlePostContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePostContent record);

}