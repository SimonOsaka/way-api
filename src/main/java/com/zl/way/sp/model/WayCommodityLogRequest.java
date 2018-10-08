package com.zl.way.sp.model;

public class WayCommodityLogRequest {

    private Long commodityId;

    private Byte type;

    public Long getCommodityId() {

        return commodityId;
    }

    public void setCommodityId(Long commodityId) {

        this.commodityId = commodityId;
    }

    public Byte getType() {

        return type;
    }

    public void setType(Byte type) {

        this.type = type;
    }
}