package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayShopLog;
import com.zl.way.mp.model.WayShopLogCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mpWayShopLogMapper")
public interface WayShopLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayShopLog record);

    int insertSelective(WayShopLog record);

    WayShopLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayShopLog record);

    int updateByPrimaryKey(WayShopLog record);

    List<WayShopLog> selectByCondition(@Param("condition") WayShopLogCondition condition,
        @Param("pageable") Pageable pageable);
}