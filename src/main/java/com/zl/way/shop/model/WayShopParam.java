package com.zl.way.shop.model;

import java.math.BigDecimal;

public class WayShopParam extends WayShop {

	private String commodityName;
	private BigDecimal clientLat;//纬度
	private BigDecimal clientLng;//经度
	private String cityCode;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
