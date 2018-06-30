package com.zl.way.shop.service;

import com.zl.way.shop.model.WayShop;
import com.zl.way.shop.model.WayShopBo;
import com.zl.way.shop.model.WayShopParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface WayShopService {

    /**
     * 获取商家信息
     *
     * @param id
     * @return
     */
    WayShop getPromoShopDetail(Long id);

    /**
     * 根据条件查询商家信息
     *
     * @param wayShopParam
     * @param pageParam
     * @return
     */
    List<WayShopBo> pageWayShopByCondition(WayShopParam wayShopParam, PageParam pageParam);
}
