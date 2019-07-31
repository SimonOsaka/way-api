package com.zl.way.mp.model;

public class WayShopQualificationResponse {

    private WayShopQualificationBo shopQualificationBo;
    private WayShopExtraBo shopExtraBo;

    public WayShopQualificationBo getShopQualificationBo() {

        return shopQualificationBo;
    }

    public void setShopQualificationBo(WayShopQualificationBo shopQualificationBo) {

        this.shopQualificationBo = shopQualificationBo;
    }

    public WayShopExtraBo getShopExtraBo() {
        return shopExtraBo;
    }

    public void setShopExtraBo(WayShopExtraBo shopExtraBo) {
        this.shopExtraBo = shopExtraBo;
    }
}