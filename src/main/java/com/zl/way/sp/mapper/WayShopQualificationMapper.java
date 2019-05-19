package com.zl.way.sp.mapper;

import com.zl.way.sp.model.WayShopQualification;
import com.zl.way.sp.model.WayShopQualificationCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("spWayShopQualificationMapper")
public interface WayShopQualificationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayShopQualification record);

    int insertSelective(WayShopQualification record);

    WayShopQualification selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayShopQualification record);

    int updateByPrimaryKey(WayShopQualification record);

    List<WayShopQualification> selectByCondition(@Param("condition") WayShopQualificationCondition condition,
        @Param("pageable") Pageable pageable);
}