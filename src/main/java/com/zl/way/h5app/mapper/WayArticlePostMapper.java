package com.zl.way.h5app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.h5app.mapper.model.WayArticlePost;

@Repository
public interface WayArticlePostMapper {

    WayArticlePost selectByPrimaryKey(Long id);

    List<WayArticlePost> querySelective(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);

    Integer querySelectiveTotal(@Param("keyword") String keyword, @Param("pageable") Pageable pageable);
}