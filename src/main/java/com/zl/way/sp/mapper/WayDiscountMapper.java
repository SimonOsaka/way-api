package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayDiscount;
import com.zl.way.sp.model.WayDiscountCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spWayDiscountMapper")
public interface WayDiscountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayDiscount record);

    int insertSelective(WayDiscount record);

    WayDiscount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayDiscount record);

    int updateByPrimaryKey(WayDiscount record);

    List<WayDiscount> selectByCondition(@Param("condition") WayDiscountCondition condition,
        @Param("pageable") Pageable pageable);

    Long countByCondition(@Param("condition") WayDiscountCondition condition);
}