package com.zl.way.discount.mapper;

import com.zl.way.discount.model.WayDiscountReal;
import com.zl.way.discount.model.WayDiscountRealQueryCondition;
import org.apache.ibatis.annotations.Param;

public interface WayDiscountRealMapper {
	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table way_discount_real
	 *
	 * @mbg.generated Fri Jul 06 17:36:56 CST 2018
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table way_discount_real
	 *
	 * @mbg.generated Fri Jul 06 17:36:56 CST 2018
	 */
	int insert(WayDiscountReal record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table way_discount_real
	 *
	 * @mbg.generated Fri Jul 06 17:36:56 CST 2018
	 */
	int insertSelective(WayDiscountReal record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table way_discount_real
	 *
	 * @mbg.generated Fri Jul 06 17:36:56 CST 2018
	 */
	WayDiscountReal selectByPrimaryKey(Long id);

	WayDiscountReal selectByDiscountIdAndRealUserLoginId(
			@Param("condition") WayDiscountRealQueryCondition condition);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table way_discount_real
	 *
	 * @mbg.generated Fri Jul 06 17:36:56 CST 2018
	 */
	int updateByPrimaryKeySelective(WayDiscountReal record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table way_discount_real
	 *
	 * @mbg.generated Fri Jul 06 17:36:56 CST 2018
	 */
	int updateByPrimaryKey(WayDiscountReal record);
}