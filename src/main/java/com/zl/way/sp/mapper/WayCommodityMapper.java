package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayCommodity;
import com.zl.way.sp.model.WayCommodityCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spWayCommodityMapper")
public interface WayCommodityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayCommodity record);

    int insertSelective(WayCommodity record);

    WayCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayCommodity record);

    int updateByPrimaryKey(WayCommodity record);

    List<WayCommodity> selectByCondition(@Param("condition") WayCommodityCondition condition,
            @Param("pageable") Pageable pageable);
}