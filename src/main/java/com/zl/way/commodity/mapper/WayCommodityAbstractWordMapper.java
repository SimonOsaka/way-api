package com.zl.way.commodity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.commodity.mapper.model.WayCommodityAbstractWordCondition;
import com.zl.way.commodity.model.WayCommodityAbstractWord;

@Repository
public interface WayCommodityAbstractWordMapper {

    WayCommodityAbstractWord selectByPrimaryKey(Integer id);

    List<WayCommodityAbstractWord> selectByCondition(@Param("condition") WayCommodityAbstractWordCondition condition,
        @Param("pageable") Pageable pageable);
}