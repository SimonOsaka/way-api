package com.zl.way.sp.enums;

import com.zl.way.base.BaseEnum;

public enum WayShopLogTypeEnum implements BaseEnum {
    STATUS((byte)1, "修改商家状态"), INFO((byte)2, "修改商家信息"), REJECT((byte)3, "驳回商家审核");

    private byte type;

    private String desc;

    WayShopLogTypeEnum(byte type, String desc) {

        this.type = type;
        this.desc = desc;
    }

    @Override
    public Byte getValue() {

        return type;
    }

    @Override
    public String getDesc() {

        return desc;
    }

}
