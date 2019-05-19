package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayCommodityLog;
import com.zl.way.sp.model.WayCommodityLogCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

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