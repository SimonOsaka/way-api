package com.zl.way.mp.model;

import java.util.List;

public class WayDiscountResponse {

    private List<WayDiscountBo> discountBoList;

    private Long discountTotal;

    public List<WayDiscountBo> getDiscountBoList() {

        return discountBoList;
    }

    public void setDiscountBoList(List<WayDiscountBo> discountBoList) {

        this.discountBoList = discountBoList;
    }

    public Long getDiscountTotal() {

        return discountTotal;
    }

    public void setDiscountTotal(Long discountTotal) {

        this.discountTotal = discountTotal;
    }
}