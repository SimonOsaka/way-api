package com.zl.way.sp.enums;

import com.zl.way.base.BaseEnum;

public enum WayShopLogSourceEnum implements BaseEnum {
    MP((byte)1, "MP"), SP((byte)2, "SP");

    private byte source;

    private String desc;

    WayShopLogSourceEnum(byte source, String desc) {

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
