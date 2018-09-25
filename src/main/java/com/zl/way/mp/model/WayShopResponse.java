package com.zl.way.mp.model;

import java.util.List;
import java.util.Map;

public class WayShopResponse {

    private List<WayShopBo> shopBoList;

    private WayShopBo shopBo;

    private Map<String, String> shopStatusMap;

    private Long shopBoTotal;

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

    public Map<String, String> getShopStatusMap() {

        return shopStatusMap;
    }

    public void setShopStatusMap(Map<String, String> shopStatusMap) {

        this.shopStatusMap = shopStatusMap;
    }

    public Long getShopBoTotal() {

        return shopBoTotal;
    }

    public void setShopBoTotal(Long shopBoTotal) {

        this.shopBoTotal = shopBoTotal;
    }
}
