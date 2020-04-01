package com.zl.way.sp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.sp.mapper.model.WayArticlePostQuery;
import com.zl.way.sp.model.WayArticlePost;

@Repository("spWayArticlePostMapper")
public interface WayArticlePostMapper {

    int insertSelective(WayArticlePost record);

    WayArticlePost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePost record);

    List<WayArticlePost> querySelective(@Param("condition") WayArticlePostQuery condition,
        @Param("pageable") Pageable pageable);
}