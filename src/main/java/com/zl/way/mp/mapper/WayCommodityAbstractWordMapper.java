package com.zl.way.mp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.mp.model.WayCommodityAbstractWord;
import com.zl.way.mp.model.WayCommodityAbstractWordCondition;

@Repository("mpWayCommodityAbstractWordMapper")
public interface WayCommodityAbstractWordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WayCommodityAbstractWord record);

    int insertSelective(WayCommodityAbstractWord record);

    WayCommodityAbstractWord selectByPrimaryKey(Integer id);

    List<WayCommodityAbstractWord> selectByPrimaryKeys(@Param("ids") List<Integer> ids);

    int updateByPrimaryKeySelective(WayCommodityAbstractWord record);

    int updateByPrimaryKey(WayCommodityAbstractWord record);

    int updateLeafByPrimaryKey(WayCommodityAbstractWord record);

    List<WayCommodityAbstractWord> selectByCondition(@Param("condition") WayCommodityAbstractWordCondition condition,
        @Param("pageable") Pageable pageable);
}