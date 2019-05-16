package com.zl.way.shop.model;

import java.math.BigDecimal;

public class WayShopQueryCondition extends WayShop {
    /**
     * 纬度
     */
    private BigDecimal clientLat;
    /**
     * 经度
     */
    private BigDecimal clientLng;
    /**
     * 抽象词id
     */
    private Integer pathPid;

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

    public Integer getPathPid() {
        return pathPid;
    }

    public void setPathPid(Integer pathPid) {
        this.pathPid = pathPid;
    }
}
