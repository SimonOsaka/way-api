package com.zl.way.mp.service;

import com.zl.way.mp.model.WayCommodityBo;
import com.zl.way.mp.model.WayCommodityParam;
import com.zl.way.util.PageParam;

import java.util.List;
import java.util.Map;

public interface WayCommodityService {

    List<WayCommodityBo> queryCommodityList(WayCommodityParam commodityParam, PageParam pageParam);

    //    WayCommodityBo getCommodity(WayCommodityParam commodityParam);
    //
    //    WayCommodityBo createCommodity(WayCommodityParam commodityParam);
    //
    //    WayCommodityBo updateCommodity(WayCommodityParam commodityParam);

    //    WayCommodityBo deleteCommodity(WayCommodityParam commodityParam);

    Map<String, String> getAllCommodityStatus();

    WayCommodityBo updateCommodityStatus(WayCommodityParam commodityParam);

    Long queryCommodityCount(WayCommodityParam shopParam);
}
