package com.zl.way.commodity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.commodity.mapper.model.WayCommodityAbstractWordRelationshipCondition;
import com.zl.way.commodity.model.WayCommodityAbstractWordRelationship;

@Repository
public interface WayCommodityAbstractWordRelationshipMapper {

    WayCommodityAbstractWordRelationship selectByPrimaryKey(Integer id);

    List<WayCommodityAbstractWordRelationship> selectByCondition(
        @Param("condition") WayCommodityAbstractWordRelationshipCondition condition,
        @Param("pageable") Pageable pageable);
}