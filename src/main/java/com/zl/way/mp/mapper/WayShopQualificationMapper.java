package com.zl.way.mp.mapper;

import com.zl.way.mp.model.WayShopQualification;
import org.springframework.stereotype.Repository;

@Repository("mpWayShopQualificationMapper")
public interface WayShopQualificationMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayShopQualification record);

    int insertSelective(WayShopQualification record);

    WayShopQualification selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayShopQualification record);

    int updateByPrimaryKey(WayShopQualification record);
}