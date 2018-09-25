package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayShopCateLeaf;
import org.springframework.stereotype.Repository;

@Repository("mpWayShopCateLeafMapper")
public interface WayShopCateLeafMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WayShopCateLeaf record);

    int insertSelective(WayShopCateLeaf record);

    WayShopCateLeaf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WayShopCateLeaf record);

    int updateByPrimaryKey(WayShopCateLeaf record);
}