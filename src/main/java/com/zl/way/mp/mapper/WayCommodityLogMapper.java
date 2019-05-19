package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayCommodityLog;
import com.zl.way.mp.model.WayCommodityLogCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpWayCommodityLogMapper")
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