package com.zl.way.shop.mapper;

import com.zl.way.shop.model.WayShop;
import com.zl.way.shop.model.WayShopQueryCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WayShopMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table way_shop
     *
     * @mbg.generated Fri Apr 06 16:04:02 CST 2018
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table way_shop
     *
     * @mbg.generated Fri Apr 06 16:04:02 CST 2018
     */
    int insert(WayShop record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table way_shop
     *
     * @mbg.generated Fri Apr 06 16:04:02 CST 2018
     */
    int insertSelective(WayShop record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table way_shop
     *
     * @mbg.generated Fri Apr 06 16:04:02 CST 2018
     */
    WayShop selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table way_shop
     *
     * @mbg.generated Fri Apr 06 16:04:02 CST 2018
     */
    int updateByPrimaryKeySelective(WayShop record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table way_shop
     *
     * @mbg.generated Fri Apr 06 16:04:02 CST 2018
     */
    int updateByPrimaryKey(WayShop record);

    List<WayShop> selectByCondition(@Param("condition") WayShopQueryCondition condition, @Param("pageable") Pageable pageable);
}