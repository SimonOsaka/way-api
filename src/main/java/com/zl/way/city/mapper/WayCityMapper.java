package com.zl.way.city.mapper;

import com.zl.way.city.model.WayCity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WayCityMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(WayCity record);

    int insertSelective(WayCity record);

    WayCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WayCity record);

    int updateByPrimaryKey(WayCity record);

    List<WayCity> selectAllByCondition(@Param("isUsed") Byte isUsed);
}