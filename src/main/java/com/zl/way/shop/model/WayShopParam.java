package com.zl.way.shop.model;

import java.math.BigDecimal;

/**
 * @author xuzhongliang
 */
public class WayShopParam extends WayShop {

    private String commodityName;
    /**
     * 纬度
     */
    private BigDecimal clientLat;
    /**
     * 经度
     */
    private BigDecimal clientLng;
    private String cityCode;

    @Override
    public String getCommodityName() {
        return commodityName;
    }

    @Override
    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getClientLat() {
        return clientLat;
    }

    public void setClientLat(BigDecimal clientLat) {
        this.clientLat = clientLat;
    }

    public BigDecimal getClientLng() {
        return clientLng;
    }

    public void setClientLng(BigDecimal clientLng) {
        this.clientLng = clientLng;
    }

    @Override
    public String getCityCode() {
        return cityCode;
    }

    @Override
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
