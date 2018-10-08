package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayShopLog;
import com.zl.way.sp.model.WayShopLogCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WayShopLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayShopLog record);

    int insertSelective(WayShopLog record);

    WayShopLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayShopLog record);

    int updateByPrimaryKey(WayShopLog record);

    List<WayShopLog> selectByCondition(@Param("condition") WayShopLogCondition condition,
            @Param("pageable") Pageable pageable);
}