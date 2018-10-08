package com.zl.way.mp.enums;

import com.zl.way.base.BaseEnum;

public enum WayCommodityStatusEnum implements BaseEnum {
    NORMAL((byte) 0, "上架"), DELETED((byte) 1, "删除"), AUDITTING((byte) 2, "审核中"), DRAFT((byte) 3,
            "草稿"), OFFLINE((byte) 4, "下架"), PENDING((byte) 5, "待上架");

    private byte status;

    private String desc;

    WayCommodityStatusEnum(byte status, String desc) {

        this.status = status;
        this.desc = desc;
    }

    @Override
    public Byte getValue() {

        return status;
    }

    @Override
    public String getDesc() {

        return desc;
    }

}
