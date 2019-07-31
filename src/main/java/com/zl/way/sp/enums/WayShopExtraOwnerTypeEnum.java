package com.zl.way.sp.enums;

import com.zl.way.base.BaseEnum;

public enum WayShopExtraOwnerTypeEnum implements BaseEnum {
    DEFAULT((byte)0, "未知"), SHOP_OWNER((byte)1, "商家自行创建"), SHOP_MANAGER((byte)2, "管理人员创建");

    private byte ownerType;

    private String desc;

    WayShopExtraOwnerTypeEnum(byte ownerType, String desc) {

        this.ownerType = ownerType;
        this.desc = desc;
    }

    @Override
    public Byte getValue() {

        return ownerType;
    }

    @Override
    public String getDesc() {

        return desc;
    }

}
