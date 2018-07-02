package com.zl.way.discount.api.model;

import java.math.BigDecimal;

public class WayDiscountResponse {

	private Long id;

	private String commodityName;

	private String commodityCate;

	private BigDecimal commodityPrice;

	private String shopPosition;

	private Integer commodityReal;

	private String shopDistance;

	private Long limitTimeExpireMills;

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

	public Integer getCommodityReal() {
		return commodityReal;
	}

	public void setCommodityReal(Integer commodityReal) {
		this.commodityReal = commodityReal;
	}

	public String getShopDistance() {
		return shopDistance;
	}

	public void setShopDistance(String shopDistance) {
		this.shopDistance = shopDistance;
	}

	public Long getLimitTimeExpireMills() {
		return limitTimeExpireMills;
	}

	public void setLimitTimeExpireMills(Long limitTimeExpireMills) {
		this.limitTimeExpireMills = limitTimeExpireMills;
	}
}