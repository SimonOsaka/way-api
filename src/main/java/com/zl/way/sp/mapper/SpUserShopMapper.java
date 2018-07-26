package com.zl.way.sp.mapper;

import com.zl.way.sp.model.SpUserShop;
import com.zl.way.sp.model.SpUserShopCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("spUserShopMapper")
public interface SpUserShopMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SpUserShop record);

    int insertSelective(SpUserShop record);

    SpUserShop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpUserShop record);

    int updateByPrimaryKey(SpUserShop record);

    SpUserShop selectByCondition(@Param("condition") SpUserShopCondition condition);
}