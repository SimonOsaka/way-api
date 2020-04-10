package com.zl.way.mp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.mp.mapper.model.WayArticlePost;

@Repository("mpWayArticlePostMapper")
public interface WayArticlePostMapper {

    int insertSelective(WayArticlePost record);

    WayArticlePost selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayArticlePost record);

    List<WayArticlePost> querySelective(@Param("keyword") String keyword, @Param("postId") Long postId,
        @Param("pageable") Pageable pageable);

    Integer querySelectiveTotal(@Param("keyword") String keyword, @Param("postId") Long postId,
        @Param("pageable") Pageable pageable);
}