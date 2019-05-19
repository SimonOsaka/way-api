package com.zl.way.sp.model;

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

    private String shopHeadTel;

    // 商家资质开始
    private String idcardFrontImgUrl;

    private String idcardBackImgUrl;

    private String idcardHandImgUrl;

    private String businessLicenseImgUrl;

    private String shopOutsideImgUrl;

    private String shopInsideImgUrl;

    private String other1ImgUrl;

    private String other2ImgUrl;

    private String other3ImgUrl;

    private String other4ImgUrl;

    private String other5ImgUrl;
    // 商家资质结束

    // 更新类型：保存=save、提交=submit
    private String updateType;

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

    public String getShopHeadTel() {

        return shopHeadTel;
    }

    public void setShopHeadTel(String shopHeadTel) {

        this.shopHeadTel = shopHeadTel;
    }

    public String getAdCode() {

        return adCode;
    }

    public void setAdCode(String adCode) {

        this.adCode = adCode;
    }

    public String getIdcardFrontImgUrl() {

        return idcardFrontImgUrl;
    }

    public void setIdcardFrontImgUrl(String idcardFrontImgUrl) {

        this.idcardFrontImgUrl = idcardFrontImgUrl;
    }

    public String getIdcardBackImgUrl() {

        return idcardBackImgUrl;
    }

    public void setIdcardBackImgUrl(String idcardBackImgUrl) {

        this.idcardBackImgUrl = idcardBackImgUrl;
    }

    public String getIdcardHandImgUrl() {

        return idcardHandImgUrl;
    }

    public void setIdcardHandImgUrl(String idcardHandImgUrl) {

        this.idcardHandImgUrl = idcardHandImgUrl;
    }

    public String getBusinessLicenseImgUrl() {

        return businessLicenseImgUrl;
    }

    public void setBusinessLicenseImgUrl(String businessLicenseImgUrl) {

        this.businessLicenseImgUrl = businessLicenseImgUrl;
    }

    public String getShopOutsideImgUrl() {

        return shopOutsideImgUrl;
    }

    public void setShopOutsideImgUrl(String shopOutsideImgUrl) {

        this.shopOutsideImgUrl = shopOutsideImgUrl;
    }

    public String getShopInsideImgUrl() {

        return shopInsideImgUrl;
    }

    public void setShopInsideImgUrl(String shopInsideImgUrl) {

        this.shopInsideImgUrl = shopInsideImgUrl;
    }

    public String getOther1ImgUrl() {

        return other1ImgUrl;
    }

    public void setOther1ImgUrl(String other1ImgUrl) {

        this.other1ImgUrl = other1ImgUrl;
    }

    public String getOther2ImgUrl() {

        return other2ImgUrl;
    }

    public void setOther2ImgUrl(String other2ImgUrl) {

        this.other2ImgUrl = other2ImgUrl;
    }

    public String getOther3ImgUrl() {

        return other3ImgUrl;
    }

    public void setOther3ImgUrl(String other3ImgUrl) {

        this.other3ImgUrl = other3ImgUrl;
    }

    public String getOther4ImgUrl() {

        return other4ImgUrl;
    }

    public void setOther4ImgUrl(String other4ImgUrl) {

        this.other4ImgUrl = other4ImgUrl;
    }

    public String getOther5ImgUrl() {

        return other5ImgUrl;
    }

    public void setOther5ImgUrl(String other5ImgUrl) {

        this.other5ImgUrl = other5ImgUrl;
    }

    public String getUpdateType() {

        return updateType;
    }

    public void setUpdateType(String updateType) {

        this.updateType = updateType;
    }
}
