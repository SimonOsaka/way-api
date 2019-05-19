package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayCommodityAbstractWord;
import com.zl.way.mp.model.WayCommodityAbstractWordCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpWayCommodityAbstractWordMapper")
public interface WayCommodityAbstractWordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WayCommodityAbstractWord record);

    int insertSelective(WayCommodityAbstractWord record);

    WayCommodityAbstractWord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WayCommodityAbstractWord record);

    int updateByPrimaryKey(WayCommodityAbstractWord record);

    int updateLeafByPrimaryKey(WayCommodityAbstractWord record);

    List<WayCommodityAbstractWord> selectByCondition(@Param("condition") WayCommodityAbstractWordCondition condition,
        @Param("pageable") Pageable pageable);
}