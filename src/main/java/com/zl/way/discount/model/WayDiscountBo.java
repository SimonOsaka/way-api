package com.zl.way.discount.model;

public class WayDiscountBo extends WayDiscount {
	private String shopDistance;

	private Long limitTimeExpireMills;

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
