package com.zl.way.mp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.mp.model.WayCommodityAbstractWordRelationship;
import com.zl.way.mp.model.WayCommodityAbstractWordRelationshipCondition;

@Repository("mpWayCommodityAbstractWordRelationshipMapper")
public interface WayCommodityAbstractWordRelationshipMapper {

    int insertSelective(WayCommodityAbstractWordRelationship record);

    WayCommodityAbstractWordRelationship selectByPrimaryKey(Integer id);

    WayCommodityAbstractWordRelationship selectByAbstractWordId(Integer id);

    int updateByPrimaryKeySelective(WayCommodityAbstractWordRelationship record);

    List<WayCommodityAbstractWordRelationship> selectByCondition(
        @Param("condition") WayCommodityAbstractWordRelationshipCondition condition,
        @Param("pageable") Pageable pageable);
}