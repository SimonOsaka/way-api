package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayShop;
import com.zl.way.mp.model.WayShopCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpWayShopMapper") public interface WayShopMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayShop record);

    int insertSelective(WayShop record);

    WayShop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayShop record);

    int updateByPrimaryKey(WayShop record);

    List<WayShop> selectByCondition(@Param("condition") WayShopCondition condition,
        @Param("pageable") Pageable pageable);

    Long countByCondition(@Param("condition") WayShopCondition condition);

    Long countAllOnline();
}