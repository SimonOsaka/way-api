package com.zl.way.discount.model;

public class WayDiscountRealQueryCondition extends WayDiscountReal {
    private Long discountId;
    private Long realUserLoginId;

    @Override
    public Long getDiscountId() {
        return discountId;
    }

    @Override
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