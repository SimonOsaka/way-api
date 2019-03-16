package com.zl.way.push.model;

import java.math.BigDecimal;

public class WayDiscountJpushBo extends WayDiscountJpush {

    private BigDecimal shopLat;

    private BigDecimal shopLng;

    private String commodityName;

    private BigDecimal commodityPrice;

    private String jpushRegId;

    public BigDecimal getShopLat() {
        return shopLat;
    }

    public void setShopLat(BigDecimal shopLat) {
        this.shopLat = shopLat;
    }

    public BigDecimal getShopLng() {
        return shopLng;
    }

    public void setShopLng(BigDecimal shopLng) {
        this.shopLng = shopLng;
    }

    public String getJpushRegId() {
        return jpushRegId;
    }

    public void setJpushRegId(String jpushRegId) {
        this.jpushRegId = jpushRegId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(BigDecimal commodityPrice) {
        this.commodityPrice = commodityPrice;
    }
}