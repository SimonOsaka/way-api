package com.zl.way.sp.mapper;

import org.springframework.stereotype.Repository;

import com.zl.way.sp.model.WayShopExtra;

@Repository("spWayShopExtraMapper")
public interface WayShopExtraMapper {

    int insertSelective(WayShopExtra record);

    WayShopExtra selectByPrimaryKey(Long id);

    WayShopExtra selectByShopId(Long shopId);

    int updateByPrimaryKeySelective(WayShopExtra record);
}