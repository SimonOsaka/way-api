package com.zl.way.h5app.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.h5app.mapper.model.WayArticlePostContent;

@Repository
public interface WayArticlePostContentMapper {

    int insertSelective(WayArticlePostContent record);

    WayArticlePostContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePostContent record);

}