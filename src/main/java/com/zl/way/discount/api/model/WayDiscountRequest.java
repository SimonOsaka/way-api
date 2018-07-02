package com.zl.way.discount.api.model;

import com.zl.way.util.PageParam;

import java.math.BigDecimal;

public class WayDiscountRequest extends PageParam {
	private Long discountId;
	private BigDecimal clientLat;//纬度
	private BigDecimal clientLng;//经度

	private String commodityName;
	private String commodityCate;
	private BigDecimal commodityPrice;
	private String shopPosition;
	private Long userLoginId;
	private Integer expireDays;

	private String discountCate;

	public String getDiscountCate() {
		return discountCate;
	}

	public void setDiscountCate(String discountCate) {
		this.discountCate = discountCate;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public BigDecimal getClientLat() {
		return clientLat;
	}

	public void setClientLat(BigDecimal clientLat) {
		this.clientLat = clientLat;
	}

	public BigDecimal getClientLng() {
		return clientLng;
	}

	public void setClientLng(BigDecimal clientLng) {
		this.clientLng = clientLng;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityCate() {
		return commodityCate;
	}

	public void setCommodityCate(String commodityCate) {
		this.commodityCate = commodityCate;
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
		this.shopPosition = shopPosition;
	}

	public Long getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(Long userLoginId) {
		this.userLoginId = userLoginId;
	}

	public Integer getExpireDays() {
		return expireDays;
	}

	public void setExpireDays(Integer expireDays) {
		this.expireDays = expireDays;
	}
}
