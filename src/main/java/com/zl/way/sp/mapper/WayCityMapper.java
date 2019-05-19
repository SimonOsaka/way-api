package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayCity;
import com.zl.way.sp.model.WayCityCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spWayCityMapper")
public interface WayCityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WayCity record);

    int insertSelective(WayCity record);

    WayCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WayCity record);

    int updateByPrimaryKey(WayCity record);

    List<WayCity> selectByCondition(@Param("condition") WayCityCondition condition,
        @Param("pageable") Pageable pageable);
}