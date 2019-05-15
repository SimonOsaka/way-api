package com.zl.way.commodity.mapper;

import com.zl.way.commodity.mapper.model.WayCommodityAbstractWordCondition;
import com.zl.way.commodity.model.WayCommodityAbstractWord;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WayCommodityAbstractWordMapper {

    WayCommodityAbstractWord selectByPrimaryKey(Integer id);

    List<WayCommodityAbstractWord> selectByCondition(@Param("condition") WayCommodityAbstractWordCondition condition,
        @Param("pageable") Pageable pageable);
}