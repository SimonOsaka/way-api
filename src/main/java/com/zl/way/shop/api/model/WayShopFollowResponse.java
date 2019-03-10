package com.zl.way.shop.api.model;

import com.zl.way.shop.model.WayShopFollowBo;

import java.util.List;

public class WayShopFollowResponse {

    List<WayShopFollowBo> shopFollowList;

    public List<WayShopFollowBo> getShopFollowList() {

        return shopFollowList;
    }

    public void setShopFollowList(List<WayShopFollowBo> shopFollowList) {

        this.shopFollowList = shopFollowList;
    }
}