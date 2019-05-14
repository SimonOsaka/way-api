package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayShopCateLeaf;
import com.zl.way.mp.model.WayShopCateLeafCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpWayShopCateLeafMapper") public interface WayShopCateLeafMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WayShopCateLeaf record);

    int insertSelective(WayShopCateLeaf record);

    WayShopCateLeaf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WayShopCateLeaf record);

    int updateByPrimaryKey(WayShopCateLeaf record);

    List<WayShopCateLeaf> selectByCondition(@Param("condition") WayShopCateLeafCondition condition);
}