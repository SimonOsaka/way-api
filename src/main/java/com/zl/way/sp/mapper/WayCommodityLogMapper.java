package com.zl.way.sp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.sp.model.WayCommodityLog;
import com.zl.way.sp.model.WayCommodityLogCondition;

@Repository("spWayCommodityLogMapper")
public interface WayCommodityLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayCommodityLog record);

    int insertSelective(WayCommodityLog record);

    WayCommodityLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayCommodityLog record);

    int updateByPrimaryKey(WayCommodityLog record);

    List<WayCommodityLog> selectByCondition(@Param("condition") WayCommodityLogCondition condition,
        @Param("pageable") Pageable pageable);
}