package com.zl.way.mp.enums;

import com.zl.way.base.BaseEnum;

public enum WayCommodityLogTypeEnum implements BaseEnum {
    STATUS((byte)1, "修改商家状态"), INFO((byte)2, "修改商品信息"), REJECT((byte)3, "驳回商家审核");

    private byte source;

    private String desc;

    WayCommodityLogTypeEnum(byte source, String desc) {

        this.source = source;
        this.desc = desc;
    }

    @Override
    public Byte getValue() {

        return source;
    }

    @Override
    public String getDesc() {

        return desc;
    }

}
