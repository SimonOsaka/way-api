package com.zl.way.shop.api.model;

import com.zl.way.util.PageParam;

public class WayShopFollowRequest extends PageParam {

    private Long shopId;

    private Long userLoginId;

    public Long getShopId() {

        return shopId;
    }

    public void setShopId(Long shopId) {

        this.shopId = shopId;
    }

    public Long getUserLoginId() {

        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {

        this.userLoginId = userLoginId;
    }
}
