package com.zl.way.sp.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.sp.model.WayArticlePostLog;

@Repository("spWayArticlePostLogMapper")
public interface WayArticlePostLogMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(WayArticlePostLog record);

    WayArticlePostLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePostLog record);

}