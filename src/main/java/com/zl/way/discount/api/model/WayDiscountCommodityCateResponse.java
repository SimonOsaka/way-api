package com.zl.way.discount.api.model;

import java.util.Map;

public class WayDiscountCommodityCateResponse {

    private Map<String, String> commodityCateMap;
    private String cateVersion;
    private String imgUrl;

    public String getCateVersion() {
        return cateVersion;
    }

    public void setCateVersion(String cateVersion) {
        this.cateVersion = cateVersion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Map<String, String> getCommodityCateMap() {
        return commodityCateMap;
    }

    public void setCommodityCateMap(Map<String, String> commodityCateMap) {
        this.commodityCateMap = commodityCateMap;
    }
}
