package com.zl.way.discount.service;

import com.zl.way.discount.model.WayDiscountBo;
import com.zl.way.discount.model.WayDiscountParam;
import com.zl.way.discount.model.WayDiscountRealBo;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayDiscountService {

	List<WayDiscountBo> selectByCondition(WayDiscountParam wayDiscountParam, PageParam pageParam);

	WayDiscountBo selectOne(WayDiscountParam wayDiscountParam);

	void createDiscount(WayDiscountParam wayDiscountParam);

	WayDiscountRealBo increaseReal(WayDiscountParam wayDiscountParam);

	WayDiscountRealBo increaseUnReal(WayDiscountParam wayDiscountParam);

	WayDiscountRealBo decreaseReal(WayDiscountParam wayDiscountParam);

	WayDiscountRealBo decreaseUnReal(WayDiscountParam wayDiscountParam);
}
