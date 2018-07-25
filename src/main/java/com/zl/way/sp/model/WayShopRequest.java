package com.zl.way.sp.model;

import com.zl.way.util.PageParam;

public class WayShopRequest extends PageParam {

    private Long id;

    private String shopName;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getShopName() {

        return shopName;
    }

    public void setShopName(String shopName) {

        this.shopName = shopName;
    }

}
