package com.zl.way.sp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WayShop implements Serializable {

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

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private String adCode;

    private String cityCode;

    private String shopPinyin;

    private String shopPy;

    private WayShopCateLeaf wayShopCateLeaf;

    private WayShopCateRoot wayShopCateRoot;

    private List<WayCommodity> commodityList;

    private static final long serialVersionUID = 1L;

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

        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopAddress() {

        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {

        this.shopAddress = shopAddress == null ? null : shopAddress.trim();
    }

    public String getShopTel() {

        return shopTel;
    }

    public void setShopTel(String shopTel) {

        this.shopTel = shopTel == null ? null : shopTel.trim();
    }

    public String getShopBusinessTime1() {

        return shopBusinessTime1;
    }

    public void setShopBusinessTime1(String shopBusinessTime1) {

        this.shopBusinessTime1 = shopBusinessTime1 == null ? null : shopBusinessTime1.trim();
    }

    public String getShopBusinessTime2() {

        return shopBusinessTime2;
    }

    public void setShopBusinessTime2(String shopBusinessTime2) {

        this.shopBusinessTime2 = shopBusinessTime2 == null ? null : shopBusinessTime2.trim();
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

        this.shopInfo = shopInfo == null ? null : shopInfo.trim();
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

        this.shopLogoUrl = shopLogoUrl == null ? null : shopLogoUrl.trim();
    }

    public Date getCreateTime() {

        return createTime;
    }

    public void setCreateTime(Date createTime) {

        this.createTime = createTime;
    }

    public Date getUpdateTime() {

        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {

        this.updateTime = updateTime;
    }

    public Byte getIsDeleted() {

        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {

        this.isDeleted = isDeleted;
    }

    public String getAdCode() {

        return adCode;
    }

    public void setAdCode(String adCode) {

        this.adCode = adCode;
    }

    public String getCityCode() {

        return cityCode;
    }

    public void setCityCode(String cityCode) {

        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getShopPinyin() {

        return shopPinyin;
    }

    public void setShopPinyin(String shopPinyin) {

        this.shopPinyin = shopPinyin == null ? null : shopPinyin.trim();
    }

    public String getShopPy() {

        return shopPy;
    }

    public void setShopPy(String shopPy) {

        this.shopPy = shopPy == null ? null : shopPy.trim();
    }

    public WayShopCateLeaf getWayShopCateLeaf() {

        return wayShopCateLeaf;
    }

    public void setWayShopCateLeaf(WayShopCateLeaf wayShopCateLeaf) {

        this.wayShopCateLeaf = wayShopCateLeaf;
    }

    public List<WayCommodity> getCommodityList() {

        return commodityList;
    }

    public void setCommodityList(List<WayCommodity> commodityList) {

        this.commodityList = commodityList;
    }

    public WayShopCateRoot getWayShopCateRoot() {

        return wayShopCateRoot;
    }

    public void setWayShopCateRoot(WayShopCateRoot wayShopCateRoot) {

        this.wayShopCateRoot = wayShopCateRoot;
    }
}