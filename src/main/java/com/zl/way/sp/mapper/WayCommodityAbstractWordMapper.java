package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayCommodityAbstractWord;
import com.zl.way.sp.model.WayCommodityAbstractWordCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spWayCommodityAbstractWordMapper")
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