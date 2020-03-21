package com.zl.way.sp.service;

import java.util.List;

import com.zl.way.sp.exception.BusinessException;
import com.zl.way.sp.model.WayCommodityBo;
import com.zl.way.sp.model.WayCommodityParam;
import com.zl.way.util.PageParam;

public interface WayCommodityService {

    List<WayCommodityBo> queryCommodityList(WayCommodityParam commodityParam, PageParam pageParam);

    WayCommodityBo getCommodity(WayCommodityParam commodityParam);

    WayCommodityBo createCommodity(WayCommodityParam commodityParam) throws BusinessException;

    WayCommodityBo updateCommodity(WayCommodityParam commodityParam) throws BusinessException;

    WayCommodityBo deleteCommodity(WayCommodityParam commodityParam);

    WayCommodityBo updateStatus(WayCommodityParam commodityParam);

    WayCommodityBo offlineCommodity(WayCommodityParam commodityParam);
}
