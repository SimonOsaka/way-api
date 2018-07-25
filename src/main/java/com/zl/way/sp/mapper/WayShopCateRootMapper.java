package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayShopCateRoot;
import com.zl.way.sp.model.WayShopCateRootCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spWayShopCateRootMapper")
public interface WayShopCateRootMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WayShopCateRoot record);

    int insertSelective(WayShopCateRoot record);

    WayShopCateRoot selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WayShopCateRoot record);

    int updateByPrimaryKey(WayShopCateRoot record);

    List<WayShopCateRoot> selectByCondition(@Param("condition") WayShopCateRootCondition condition);
}