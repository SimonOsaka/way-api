package com.zl.way.commodity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.commodity.mapper.model.WayCommodityCondition;
import com.zl.way.commodity.model.WayCommodity;

@Repository
public interface WayCommodityMapper {

    WayCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayCommodity record);

    int updateByPrimaryKey(WayCommodity record);

    List<WayCommodity> selectByCondition(@Param("condition") WayCommodityCondition condition,
        @Param("pageable") Pageable pageable);
}