package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayDiscountReal;
import org.springframework.stereotype.Repository;

@Repository("mpWayDiscountRealMapper")
public interface WayDiscountRealMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayDiscountReal record);

    int insertSelective(WayDiscountReal record);

    WayDiscountReal selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayDiscountReal record);

    int updateByPrimaryKey(WayDiscountReal record);
}