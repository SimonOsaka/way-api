package com.zl.way.commodity.mapper.model;

import java.util.List;

import com.zl.way.commodity.model.WayCommodity;

public class WayCommodityCondition extends WayCommodity {
    private List<Integer> abstractWordIdList;
    private String shopCityCode;
    private String shopAdCode;
    private String shopAdCodeExclude;
    private Integer shopCateLeafId;
    private Long idExclude;

    public List<Integer> getAbstractWordIdList() {
        return abstractWordIdList;
    }

    public void setAbstractWordIdList(List<Integer> abstractWordIdList) {
        this.abstractWordIdList = abstractWordIdList;
    }

    public String getShopCityCode() {
        return shopCityCode;
    }

    public void setShopCityCode(String shopCityCode) {
        this.shopCityCode = shopCityCode;
    }

    public String getShopAdCode() {
        return shopAdCode;
    }

    public void setShopAdCode(String shopAdCode) {
        this.shopAdCode = shopAdCode;
    }

    public String getShopAdCodeExclude() {
        return shopAdCodeExclude;
    }

    public void setShopAdCodeExclude(String shopAdCodeExclude) {
        this.shopAdCodeExclude = shopAdCodeExclude;
    }

    public Integer getShopCateLeafId() {
        return shopCateLeafId;
    }

    public void setShopCateLeafId(Integer shopCateLeafId) {
        this.shopCateLeafId = shopCateLeafId;
    }

    public Long getIdExclude() {
        return idExclude;
    }

    public void setIdExclude(Long idExclude) {
        this.idExclude = idExclude;
    }
}
