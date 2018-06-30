package com.zl.way.commodity.api.model;

import com.zl.way.util.PageParam;

public class WayCommodityRequest extends PageParam {
    private String commodityName;
    private Long shopId;
    private Long commodityId;

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }
}
