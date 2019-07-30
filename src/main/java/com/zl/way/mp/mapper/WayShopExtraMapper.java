package com.zl.way.mp.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.mp.model.WayShopExtra;

@Repository("mpWayShopExtraMapper")
public interface WayShopExtraMapper {

    int insertSelective(WayShopExtra record);

    WayShopExtra selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayShopExtra record);
}