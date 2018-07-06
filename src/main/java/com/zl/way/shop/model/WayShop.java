package com.zl.way.shop.model;

import com.zl.way.commodity.model.WayCommodity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class WayShop {

	private Long id;


	private String shopName;


	private String shopAddress;


	private String shopTel;


	private String shopBusinessTime1;


	private String shopBusinessTime2;

	private Integer shopCateLeafId;


	private WayShopCateLeaf wayShopCateLeaf;


	private String shopInfo;


	private Date createTime;


	private Date updateTime;


	private Byte isDeleted;


	private BigDecimal shopLat;


	private BigDecimal shopLng;


	private String shopLogoUrl;

	private List<WayCommodity> commodityList;

	private String commodityName;

	private String cityCode;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public List<WayCommodity> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(List<WayCommodity> commodityList) {
		this.commodityList = commodityList;
	}


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

	public WayShopCateLeaf getWayShopCateLeaf() {
		return wayShopCateLeaf;
	}

	public void setWayShopCateLeaf(WayShopCateLeaf wayShopCateLeaf) {
		this.wayShopCateLeaf = wayShopCateLeaf;
	}


	public String getShopInfo() {
		return shopInfo;
	}


	public void setShopInfo(String shopInfo) {
		this.shopInfo = shopInfo == null ? null : shopInfo.trim();
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

	public Integer getShopCateLeafId() {
		return shopCateLeafId;
	}

	public void setShopCateLeafId(Integer shopCateLeafId) {
		this.shopCateLeafId = shopCateLeafId;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}