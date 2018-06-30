package com.zl.way.shop.model;

public class WayShopBo extends WayShop {
    private String shopCateLeafName;
    private String shopDistance;

    public String getShopCateLeafName() {
        if (null != getWayShopCateLeaf()) {
            return getWayShopCateLeaf().getCateName();
        }
        return null;
    }

    public void setShopCateLeafName(String shopCateLeafName) {
        this.shopCateLeafName = shopCateLeafName;
    }

    public String getShopDistance() {
        return shopDistance;
    }

    public void setShopDistance(String shopDistance) {
        this.shopDistance = shopDistance;
    }
}
