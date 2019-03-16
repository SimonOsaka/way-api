package com.zl.way.discount.mapper;

import com.zl.way.discount.model.WayDiscountJpush;

public interface WayDiscountJpushMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WayDiscountJpush record);

    int insertSelective(WayDiscountJpush record);

    WayDiscountJpush selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayDiscountJpush record);

    int updateByPrimaryKey(WayDiscountJpush record);
}