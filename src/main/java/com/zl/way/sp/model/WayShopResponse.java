package com.zl.way.sp.model;

import java.util.List;

public class WayShopResponse {

    private List<WayShopBo> shopBoList;

    private WayShopBo shopBo;

    public List<WayShopBo> getShopBoList() {

        return shopBoList;
    }

    public void setShopBoList(List<WayShopBo> shopBoList) {

        this.shopBoList = shopBoList;
    }

    public WayShopBo getShopBo() {

        return shopBo;
    }

    public void setShopBo(WayShopBo shopBo) {

        this.shopBo = shopBo;
    }
}
