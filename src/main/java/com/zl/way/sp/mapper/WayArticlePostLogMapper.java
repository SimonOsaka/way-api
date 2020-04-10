package com.zl.way.sp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.zl.way.sp.model.WayArticlePostLog;

@Repository("spWayArticlePostLogMapper")
public interface WayArticlePostLogMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(WayArticlePostLog record);

    WayArticlePostLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePostLog record);

    List<WayArticlePostLog> queryArticlePostLog(@Param("postId") Long postId, @Param("eventType") Byte eventType);
}