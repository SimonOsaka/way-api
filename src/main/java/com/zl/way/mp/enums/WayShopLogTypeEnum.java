package com.zl.way.mp.enums;

import com.zl.way.base.BaseEnum;

public enum WayShopLogTypeEnum implements BaseEnum {
    STATUS((byte)1, "修改商家状态"), INFO((byte)2, "修改商家信息"), REJECT((byte)3, "驳回商家审核");

    private byte source;

    private String desc;

    WayShopLogTypeEnum(byte source, String desc) {

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
