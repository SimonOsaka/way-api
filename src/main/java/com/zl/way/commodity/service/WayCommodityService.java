package com.zl.way.commodity.service;

import com.zl.way.commodity.model.WayCommodity;
import com.zl.way.commodity.model.WayCommodityBo;
import com.zl.way.commodity.model.WayCommodityParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayCommodityService {

    /**
     * 获取商品信息
     *
     * @param id
     * @return
     */
    WayCommodity getCommodityDetail(Long id);

    /**
     * 根据条件查询商品信息
     *
     * @param wayCommodityParam
     * @param pageParam
     * @return
     */
    List<WayCommodityBo> pageCommodityByCondition(WayCommodityParam wayCommodityParam, PageParam pageParam);
}
