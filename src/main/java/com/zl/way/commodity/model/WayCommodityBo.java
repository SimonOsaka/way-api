package com.zl.way.commodity.model;

import com.zl.way.shop.model.WayShopBo;

public class WayCommodityBo extends WayCommodity {
    private WayShopBo wayShop;

    public WayShopBo getWayShop() {
        return wayShop;
    }

    public void setWayShop(WayShopBo wayShop) {
        this.wayShop = wayShop;
    }

}
