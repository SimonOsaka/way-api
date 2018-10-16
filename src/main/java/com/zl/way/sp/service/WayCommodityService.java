package com.zl.way.sp.service;

import com.zl.way.sp.model.WayCommodityBo;
import com.zl.way.sp.model.WayCommodityParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayCommodityService {

    List<WayCommodityBo> queryCommodityList(WayCommodityParam commodityParam, PageParam pageParam);

    WayCommodityBo getCommodity(WayCommodityParam commodityParam);

    WayCommodityBo createCommodity(WayCommodityParam commodityParam);

    WayCommodityBo updateCommodity(WayCommodityParam commodityParam);

    WayCommodityBo deleteCommodity(WayCommodityParam commodityParam);

    WayCommodityBo updateStatus(WayCommodityParam commodityParam);

    WayCommodityBo offlineCommodity(WayCommodityParam commodityParam);
}
