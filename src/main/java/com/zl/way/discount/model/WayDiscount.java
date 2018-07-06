package com.zl.way.discount.model;

import java.math.BigDecimal;
import java.util.Date;


public class WayDiscount {

	private Long id;


	private String commodityName;


	private String commodityCate;


	private BigDecimal commodityPrice;


	private String shopPosition;


	private BigDecimal shopLng;


	private BigDecimal shopLat;


	private Long userLoginId;


	private Integer commodityReal;

	private Date limitTimeExpire;


	private Date createTime;


	private Date updateTime;


	private Byte isDeleted;

	private String cityCode;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCommodityName() {
		return commodityName;
	}


	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName == null ? null : commodityName.trim();
	}


	public String getCommodityCate() {
		return commodityCate;
	}


	public void setCommodityCate(String commodityCate) {
		this.commodityCate = commodityCate == null ? null : commodityCate.trim();
	}


	public BigDecimal getCommodityPrice() {
		return commodityPrice;
	}


	public void setCommodityPrice(BigDecimal commodityPrice) {
		this.commodityPrice = commodityPrice;
	}


	public String getShopPosition() {
		return shopPosition;
	}


	public void setShopPosition(String shopPosition) {
		this.shopPosition = shopPosition == null ? null : shopPosition.trim();
	}


	public BigDecimal getShopLng() {
		return shopLng;
	}


	public void setShopLng(BigDecimal shopLng) {
		this.shopLng = shopLng;
	}


	public BigDecimal getShopLat() {
		return shopLat;
	}


	public void setShopLat(BigDecimal shopLat) {
		this.shopLat = shopLat;
	}


	public Long getUserLoginId() {
		return userLoginId;
	}


	public void setUserLoginId(Long userLoginId) {
		this.userLoginId = userLoginId;
	}


	public Integer getCommodityReal() {
		return commodityReal;
	}


	public void setCommodityReal(Integer commodityReal) {
		this.commodityReal = commodityReal;
	}

	public Date getLimitTimeExpire() {
		return limitTimeExpire;
	}

	public void setLimitTimeExpire(Date limitTimeExpire) {
		this.limitTimeExpire = limitTimeExpire;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}