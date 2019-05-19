package com.zl.way.mp.mapper;

import com.zl.way.mp.model.SpUserShop;
import com.zl.way.mp.model.SpUserShopCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpSpUserShopMapper")
public interface SpUserShopMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SpUserShop record);

    int insertSelective(SpUserShop record);

    SpUserShop selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpUserShop record);

    int updateByPrimaryKey(SpUserShop record);

    List<SpUserShop> selectByCondition(@Param("condition") SpUserShopCondition condition,
                                       @Param("pageable") Pageable pageable);
}