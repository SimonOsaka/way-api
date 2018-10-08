package com.zl.way.mp.model;

public class WayCommodityLogRequest {

    private Long commodityId;

    private Byte type;

    private Byte source;

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

    public Byte getSource() {

        return source;
    }

    public void setSource(Byte source) {

        this.source = source;
    }
}