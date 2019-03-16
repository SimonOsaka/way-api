package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayDiscountJpush;
import org.springframework.stereotype.Repository;

@Repository("spWayDiscountJpushMapper") public interface WayDiscountJpushMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WayDiscountJpush record);

    int insertSelective(WayDiscountJpush record);

    WayDiscountJpush selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayDiscountJpush record);

    int updateByPrimaryKey(WayDiscountJpush record);
}