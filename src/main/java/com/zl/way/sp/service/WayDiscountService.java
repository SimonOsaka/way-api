package com.zl.way.sp.service;

import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayDiscountBo;
import com.zl.way.sp.model.WayDiscountParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayDiscountService {

    List<WayDiscountBo> queryDiscountList(WayDiscountParam param, PageParam pageParam);

    WayDiscountBo getDiscount(WayDiscountParam param);

    WayDiscountBo createDiscount(WayDiscountParam param) throws BusinessException;
}
