package com.zl.way.mp.service;

import com.zl.way.mp.model.WayDiscountBo;
import com.zl.way.mp.model.WayDiscountParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayDiscountService {

    List<WayDiscountBo> selectByCondition(WayDiscountParam wayDiscountParam, PageParam pageParam);

    WayDiscountBo deleteByCondition(WayDiscountParam wayDiscountParam);

    Long countByCondition(WayDiscountParam wayDiscountParam);
}
