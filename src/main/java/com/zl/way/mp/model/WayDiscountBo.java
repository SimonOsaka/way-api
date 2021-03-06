package com.zl.way.mp.model;

public class WayDiscountBo extends WayDiscount {

    private String shopDistance;

    private Long limitTimeExpireMills;

    private String staticMapUrl;

    private String commodityImageUrl;

    private Byte realType;

    public Byte getRealType() {

        return realType;
    }

    public void setRealType(Byte realType) {

        this.realType = realType;
    }

    public String getShopDistance() {

        return shopDistance;
    }

    public void setShopDistance(String shopDistance) {

        this.shopDistance = shopDistance;
    }

    public Long getLimitTimeExpireMills() {

        return limitTimeExpireMills;
    }

    public void setLimitTimeExpireMills(Long limitTimeExpireMills) {

        this.limitTimeExpireMills = limitTimeExpireMills;
    }

    public String getStaticMapUrl() {

        return staticMapUrl;
    }

    public void setStaticMapUrl(String staticMapUrl) {

        this.staticMapUrl = staticMapUrl;
    }

    public String getCommodityImageUrl() {

        return commodityImageUrl;
    }

    public void setCommodityImageUrl(String commodityImageUrl) {

        this.commodityImageUrl = commodityImageUrl;
    }

}
