package com.zl.way.sp.enums;

public enum WayShopStatus {
    NORMAL((byte) 0, "上线"), DELETED((byte) 1, "删除"), AUDITTING((byte) 2, "审核中"), DRAFT((byte) 3,
            "草稿"), OFFLINE((byte) 4, "下线"), PENDING((byte) 5, "待上线");

    private byte status;

    private String desc;

    private WayShopStatus(byte status, String desc) {

        this.status = status;
        this.desc = desc;
    }

    public byte getStatus() {

        return status;
    }

    public String getDesc() {

        return desc;
    }

    public static WayShopStatus getStatus(byte val) {

        WayShopStatus[] wayShopStatuses = WayShopStatus.values();
        for (WayShopStatus shopStatus : wayShopStatuses) {
            if (shopStatus.getStatus() == val) {
                return shopStatus;
            }
        }

        throw new RuntimeException("商铺状态不存在");
    }
}
