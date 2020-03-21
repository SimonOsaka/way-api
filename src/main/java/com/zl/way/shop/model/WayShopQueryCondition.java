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
    /**
     * 商品名称完整拼音
     */
    private String commodityNamePinyin;
    /**
     * 商品名称缩写拼音
     */
    private String commodityNamePy;

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

    public String getCommodityNamePinyin() {
        return commodityNamePinyin;
    }

    public void setCommodityNamePinyin(String commodityNamePinyin) {
        this.commodityNamePinyin = commodityNamePinyin;
    }

    public String getCommodityNamePy() {
        return commodityNamePy;
    }

    public void setCommodityNamePy(String commodityNamePy) {
        this.commodityNamePy = commodityNamePy;
    }
}
