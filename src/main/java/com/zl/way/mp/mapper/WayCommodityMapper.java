package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayCommodity;
import org.springframework.stereotype.Repository;

@Repository("mpWayCommodityMapper")
public interface WayCommodityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayCommodity record);

    int insertSelective(WayCommodity record);

    WayCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayCommodity record);

    int updateByPrimaryKey(WayCommodity record);
}