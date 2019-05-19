package com.zl.way.www.mapper;

import com.zl.way.www.model.WayLand;
import org.springframework.stereotype.Repository;

@Repository
public interface WayLandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WayLand record);

    int insertSelective(WayLand record);

    WayLand selectByPrimaryKey(Integer id);

    WayLand selectByPropKey(String propKey);

    int updateByPrimaryKeySelective(WayLand record);

    int updateByPrimaryKey(WayLand record);
}