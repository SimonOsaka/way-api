package com.zl.way.push.mapper;

import com.zl.way.push.model.WayDiscountJpush;
import com.zl.way.push.model.WayDiscountJpushBo;
import com.zl.way.push.model.WayDiscountJpushCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("pushWayDiscountJpushMapper")
public interface WayDiscountJpushMapper {

    int insertSelective(WayDiscountJpush record);

    WayDiscountJpush selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayDiscountJpush record);

    List<WayDiscountJpushBo> selectByCondition(@Param("condition") WayDiscountJpushCondition condition,
        @Param("pageable") Pageable pageable);

}