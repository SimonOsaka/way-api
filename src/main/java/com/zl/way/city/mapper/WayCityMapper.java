package com.zl.way.city.mapper;

import com.zl.way.city.model.WayCity;

import java.util.List;

public interface WayCityMapper {

    int deleteByPrimaryKey(Integer id);


    int insert(WayCity record);


    int insertSelective(WayCity record);


    WayCity selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(WayCity record);


    int updateByPrimaryKey(WayCity record);

    List<WayCity> selectAllByCondition(Byte isUsed);
}