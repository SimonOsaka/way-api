package com.zl.way.discount.api.model;

import java.math.BigDecimal;

public class WayDiscountResponse {

	private Long id;

	private String commodityName;

	private String commodityCate;

	private BigDecimal commodityPrice;

	private String shopPosition;
	private BigDecimal shopLng;
	private BigDecimal shopLat;

	private Integer commodityReal;
	private Integer commodityUnreal;

	private String shopDistance;

	private Long limitTimeExpireMills;

	private String staticMapUrl;

	private String commodityImageUrl;

	private Long discountId;

	private Long realUserLoginId;

	private Byte realType;

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

	public Integer getCommodityUnreal() {
		return commodityUnreal;
	}

	public void setCommodityUnreal(Integer commodityUnreal) {
		this.commodityUnreal = commodityUnreal;
	}

	public Byte getRealType() {
		return realType;
	}

	public void setRealType(Byte realType) {
		this.realType = realType;
	}

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

	public String getStaticMapUrl() {
		return staticMapUrl;
	}

	public void setStaticMapUrl(String staticMapUrl) {
		this.staticMapUrl = staticMapUrl;
	}

	public String getCommodityImageUrl() {
		return commodityImageUrl;
	}

	public void setCommodityImageUrl(String commodityImageUrl) {
		this.commodityImageUrl = commodityImageUrl;
	}

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public Long getRealUserLoginId() {
		return realUserLoginId;
	}

	public void setRealUserLoginId(Long realUserLoginId) {
		this.realUserLoginId = realUserLoginId;
	}
}