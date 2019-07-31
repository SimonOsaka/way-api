package com.zl.way.mp.model;

import com.zl.way.util.PageParam;

public class WayShopQualificationRequest extends PageParam {

    private Long id;

    private Long shopExtraId;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getShopExtraId() {
        return shopExtraId;
    }

    public void setShopExtraId(Long shopExtraId) {
        this.shopExtraId = shopExtraId;
    }
}