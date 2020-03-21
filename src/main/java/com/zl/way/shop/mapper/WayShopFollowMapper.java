package com.zl.way.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.zl.way.shop.model.WayShopFollow;
import com.zl.way.shop.model.WayShopFollowBo;
import com.zl.way.shop.model.WayShopFollowQueryCondition;

@Repository
public interface WayShopFollowMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WayShopFollow record);

    int insertSelective(WayShopFollow record);

    WayShopFollow selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WayShopFollow record);

    int updateByPrimaryKey(WayShopFollow record);

    List<WayShopFollowBo> selectByCondition(@Param("condition") WayShopFollowQueryCondition condition,
        @Param("pageable") Pageable pageable);
}