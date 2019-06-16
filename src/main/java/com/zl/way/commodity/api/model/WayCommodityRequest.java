package com.zl.way.commodity.api.model;

import java.math.BigDecimal;

import com.zl.way.util.PageParam;

public class WayCommodityRequest extends PageParam {
    private String commodityName;
    private Long shopId;
    private Long commodityId;
    private BigDecimal clientLng;
    private BigDecimal clientLat;

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

    public BigDecimal getClientLng() {
        return clientLng;
    }

    public void setClientLng(BigDecimal clientLng) {
        this.clientLng = clientLng;
    }

    public BigDecimal getClientLat() {
        return clientLat;
    }

    public void setClientLat(BigDecimal clientLat) {
        this.clientLat = clientLat;
    }
}
