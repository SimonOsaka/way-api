package com.zl.way.sp.model;

import com.zl.way.util.PageParam;

public class WayCommodityRequest extends PageParam {

    private Long shopId;

    public Long getShopId() {

        return shopId;
    }

    public void setShopId(Long shopId) {

        this.shopId = shopId;
    }
}
