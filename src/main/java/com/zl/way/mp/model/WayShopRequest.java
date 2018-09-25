package com.zl.way.mp.model;

import com.zl.way.util.PageParam;

import java.math.BigDecimal;

public class WayShopRequest extends PageParam {

    private Long id;

    private String shopName;

    private String shopAddress;

    private String shopTel;

    private String shopBusinessTime1;

    private String shopBusinessTime2;

    private Integer shopCateLeafId;

    private String shopInfo;

    private BigDecimal shopLat;

    private BigDecimal shopLng;

    private String shopLogoUrl;

    private String adCode;

    private String cityCode;

    private Long userLoginId;

    private Byte shopStatus;

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

    public String getShopAddress() {

        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {

        this.shopAddress = shopAddress;
    }

    public String getShopTel() {

        return shopTel;
    }

    public void setShopTel(String shopTel) {

        this.shopTel = shopTel;
    }

    public String getShopBusinessTime1() {

        return shopBusinessTime1;
    }

    public void setShopBusinessTime1(String shopBusinessTime1) {

        this.shopBusinessTime1 = shopBusinessTime1;
    }

    public String getShopBusinessTime2() {

        return shopBusinessTime2;
    }

    public void setShopBusinessTime2(String shopBusinessTime2) {

        this.shopBusinessTime2 = shopBusinessTime2;
    }

    public Integer getShopCateLeafId() {

        return shopCateLeafId;
    }

    public void setShopCateLeafId(Integer shopCateLeafId) {

        this.shopCateLeafId = shopCateLeafId;
    }

    public String getShopInfo() {

        return shopInfo;
    }

    public void setShopInfo(String shopInfo) {

        this.shopInfo = shopInfo;
    }

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

    public String getShopLogoUrl() {

        return shopLogoUrl;
    }

    public void setShopLogoUrl(String shopLogoUrl) {

        this.shopLogoUrl = shopLogoUrl;
    }

    public String getCityCode() {

        return cityCode;
    }

    public void setCityCode(String cityCode) {

        this.cityCode = cityCode;
    }

    public Long getUserLoginId() {

        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {

        this.userLoginId = userLoginId;
    }

    public String getAdCode() {

        return adCode;
    }

    public void setAdCode(String adCode) {

        this.adCode = adCode;
    }

    public Byte getShopStatus() {

        return shopStatus;
    }

    public void setShopStatus(Byte shopStatus) {

        this.shopStatus = shopStatus;
    }
}
