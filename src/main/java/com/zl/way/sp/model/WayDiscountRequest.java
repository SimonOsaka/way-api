package com.zl.way.sp.model;

import com.zl.way.util.PageParam;

import java.math.BigDecimal;
import java.util.Date;

public class WayDiscountRequest extends PageParam {

    private Long id;

    private Long commodityId;//way_commodity的id

    private BigDecimal commodityPrice;

    private String commodityCate;

    private Date limitTimeExpire;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getCommodityId() {

        return commodityId;
    }

    public void setCommodityId(Long commodityId) {

        this.commodityId = commodityId;
    }

    public BigDecimal getCommodityPrice() {

        return commodityPrice;
    }

    public void setCommodityPrice(BigDecimal commodityPrice) {

        this.commodityPrice = commodityPrice;
    }

    public Date getLimitTimeExpire() {

        return limitTimeExpire;
    }

    public void setLimitTimeExpire(Date limitTimeExpire) {

        this.limitTimeExpire = limitTimeExpire;
    }

    public String getCommodityCate() {

        return commodityCate;
    }

    public void setCommodityCate(String commodityCate) {

        this.commodityCate = commodityCate;
    }
}