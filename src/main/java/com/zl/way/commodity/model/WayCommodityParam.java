package com.zl.way.commodity.model;

import java.math.BigDecimal;

/**
 * @author xuzhongliang
 */
public class WayCommodityParam extends WayCommodity {
    /**
     * 纬度
     */
    private BigDecimal clientLat;
    /**
     * 经度
     */
    private BigDecimal clientLng;

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
}
