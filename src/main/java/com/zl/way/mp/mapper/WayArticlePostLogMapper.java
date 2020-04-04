package com.zl.way.mp.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.mp.mapper.model.WayArticlePostLog;

@Repository("mpWayArticlePostLogMapper")
public interface WayArticlePostLogMapper {

    int insertSelective(WayArticlePostLog record);

    WayArticlePostLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePostLog record);

}