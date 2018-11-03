package com.zl.way.ios;

public class WeixinShareRequest {

    private Long commodityId;

    private Long discountId;

    private String shareType;

    public Long getCommodityId() {

        return commodityId;
    }

    public void setCommodityId(Long commodityId) {

        this.commodityId = commodityId;
    }

    public Long getDiscountId() {

        return discountId;
    }

    public void setDiscountId(Long discountId) {

        this.discountId = discountId;
    }

    public String getShareType() {

        return shareType;
    }

    public void setShareType(String shareType) {

        this.shareType = shareType;
    }
}
