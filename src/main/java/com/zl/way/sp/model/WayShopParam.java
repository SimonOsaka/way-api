package com.zl.way.sp.model;

public class WayShopParam extends WayShop {

    private Long spUserLoginId;

    private WayShopQualificationParam shopQualificationParam;

    //更新类型：保存=save、提交=submit
    private String updateType;

    public Long getSpUserLoginId() {

        return spUserLoginId;
    }

    public void setSpUserLoginId(Long spUserLoginId) {

        this.spUserLoginId = spUserLoginId;
    }

    public WayShopQualificationParam getShopQualificationParam() {

        return shopQualificationParam;
    }

    public void setShopQualificationParam(WayShopQualificationParam shopQualificationParam) {

        this.shopQualificationParam = shopQualificationParam;
    }

    public String getUpdateType() {

        return updateType;
    }

    public void setUpdateType(String updateType) {

        this.updateType = updateType;
    }
}