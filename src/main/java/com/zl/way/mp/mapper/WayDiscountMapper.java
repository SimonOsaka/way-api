package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayDiscount;
import org.springframework.stereotype.Repository;

@Repository("mpWayDiscountMapper")
public interface WayDiscountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayDiscount record);

    int insertSelective(WayDiscount record);

    WayDiscount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayDiscount record);

    int updateByPrimaryKey(WayDiscount record);
}