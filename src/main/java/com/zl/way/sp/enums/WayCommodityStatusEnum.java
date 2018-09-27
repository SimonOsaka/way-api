package com.zl.way.sp.enums;

public enum WayCommodityStatusEnum {
    NORMAL((byte) 0, "上架"), DELETED((byte) 1, "删除"), AUDITTING((byte) 2, "审核中"), DRAFT((byte) 3,
            "草稿"), OFFLINE((byte) 4, "下架"), PENDING((byte) 5, "待上架");

    private byte status;

    private String desc;

    private WayCommodityStatusEnum(byte status, String desc) {

        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {

        return status;
    }

    public String getDesc() {

        return desc;
    }

    public static WayCommodityStatusEnum getStatus(byte val) {

        WayCommodityStatusEnum[] wayCommodityStatusEnums = WayCommodityStatusEnum.values();
        for (WayCommodityStatusEnum commodityStatusEnum : wayCommodityStatusEnums) {
            if (commodityStatusEnum.getStatus() == val) {
                return commodityStatusEnum;
            }
        }

        return null;
    }
}
