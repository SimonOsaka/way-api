package com.zl.way.shop.api.model;

import com.zl.way.util.PageParam;

import java.math.BigDecimal;

public class WayShopRequest extends PageParam {

    private String shopName;

    private Integer shopCateLeafId;

    private Long shopId;

    private String keywords;

    private BigDecimal clientLat;//纬度

    private BigDecimal clientLng;//经度

    private String cityCode;

    private Long userLoginId;

    public String getCityCode() {

        return cityCode;
    }

    public void setCityCode(String cityCode) {

        this.cityCode = cityCode;
    }

    public String getShopName() {

        return shopName;
    }

    public void setShopName(String shopName) {

        this.shopName = shopName;
    }

    public Integer getShopCateLeafId() {

        return shopCateLeafId;
    }

    public void setShopCateLeafId(Integer shopCateLeafId) {

        this.shopCateLeafId = shopCateLeafId;
    }

    public Long getShopId() {

        return shopId;
    }

    public void setShopId(Long shopId) {

        this.shopId = shopId;
    }

    public String getKeywords() {

        return keywords;
    }

    public void setKeywords(String keywords) {

        this.keywords = keywords;
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

    public Long getUserLoginId() {

        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {

        this.userLoginId = userLoginId;
    }
}
