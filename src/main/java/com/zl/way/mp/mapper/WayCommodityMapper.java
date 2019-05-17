package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayCommodity;
import com.zl.way.mp.model.WayCommodityCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpWayCommodityMapper") public interface WayCommodityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayCommodity record);

    int insertSelective(WayCommodity record);

    WayCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayCommodity record);

    int updateByPrimaryKey(WayCommodity record);

    List<WayCommodity> selectByCondition(@Param("condition") WayCommodityCondition condition,
        @Param("pageable") Pageable pageable);

    Long countByCondition(@Param("condition") WayCommodityCondition condition);

    Long countAllOnline();
}