package com.zl.way.mp.model;

import org.apache.commons.lang3.StringUtils;

public class WayShopBo extends WayShop {

    private String shopStatusName;

    private String cityName;

    public String getShopStatusName() {

        return shopStatusName;
    }

    public void setShopStatusName(String shopStatusName) {

        this.shopStatusName = shopStatusName;
    }

    public String getCityName() {

        return cityName;
    }

    public void setCityName(String cityName) {

        this.cityName = cityName;
    }

    public String getShopBusinessTime() {

        return StringUtils.defaultIfBlank(super.getShopBusinessTime1(), StringUtils.EMPTY)
            .concat(StringUtils.defaultIfBlank(" " + super.getShopBusinessTime2(), StringUtils.EMPTY));
    }
}