package com.zl.way.mp.model;

public class WayShopLogRequest {

    private Long shopId;

    private Byte type;

    private Byte source;

    public Long getShopId() {

        return shopId;
    }

    public void setShopId(Long shopId) {

        this.shopId = shopId;
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