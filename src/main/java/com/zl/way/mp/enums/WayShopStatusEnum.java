package com.zl.way.mp.enums;

public enum WayShopStatusEnum {
    NORMAL((byte) 0, "上线"), DELETED((byte) 1, "删除"), AUDITTING((byte) 2, "审核中"), DRAFT((byte) 3,
            "草稿"), OFFLINE((byte) 4, "下线"), PENDING((byte) 5, "待上线");

    private byte status;

    private String desc;

    private WayShopStatusEnum(byte status, String desc) {

        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {

        return status;
    }

    public String getDesc() {

        return desc;
    }

    public static WayShopStatusEnum getStatus(byte val) {

        WayShopStatusEnum[] wayShopStatuses = WayShopStatusEnum.values();
        for (WayShopStatusEnum shopStatus : wayShopStatuses) {
            if (shopStatus.getStatus() == val) {
                return shopStatus;
            }
        }

        return null;
    }
}
