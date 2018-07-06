package com.zl.way.discount.model;

import java.math.BigDecimal;

public class WayDiscountQueryCondition extends WayDiscount {

	private Long discountId;

	private BigDecimal clientLat;//纬度
	private BigDecimal clientLng;//经度

	private Boolean limitTimeExpireEnable;

	private Long realUserLoginId;

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

	public Boolean getLimitTimeExpireEnable() {
		return limitTimeExpireEnable;
	}

	public void setLimitTimeExpireEnable(Boolean limitTimeExpireEnable) {
		this.limitTimeExpireEnable = limitTimeExpireEnable;
	}

	public Long getRealUserLoginId() {
		return realUserLoginId;
	}

	public void setRealUserLoginId(Long realUserLoginId) {
		this.realUserLoginId = realUserLoginId;
	}
}
