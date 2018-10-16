package com.zl.way.sp.model;

import java.util.List;

public class WayDiscountResponse {

    private List<WayDiscountBo> discountBoList;

    private WayDiscountBo discountBo;

    public List<WayDiscountBo> getDiscountBoList() {

        return discountBoList;
    }

    public void setDiscountBoList(List<WayDiscountBo> discountBoList) {

        this.discountBoList = discountBoList;
    }

    public WayDiscountBo getDiscountBo() {

        return discountBo;
    }

    public void setDiscountBo(WayDiscountBo discountBo) {

        this.discountBo = discountBo;
    }
}