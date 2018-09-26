package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayDiscount;
import com.zl.way.mp.model.WayDiscountQueryCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpWayDiscountMapper")
public interface WayDiscountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayDiscount record);

    int insertSelective(WayDiscount record);

    WayDiscount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayDiscount record);

    int updateByPrimaryKey(WayDiscount record);

    List<WayDiscount> selectByCondition(@Param("condition") WayDiscountQueryCondition condition,
            @Param("pageable") Pageable pageable);

    Long countByCondition(@Param("condition") WayDiscountQueryCondition condition);
}