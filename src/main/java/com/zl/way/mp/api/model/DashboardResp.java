package com.zl.way.mp.api.model;

public class DashboardResp {
    private Integer totalUsers;
    private Integer totalShops;
    private Integer totalCommodities;

    public DashboardResp(Integer totalUsers, Integer totalShops, Integer totalCommodities) {
        this.totalUsers = totalUsers;
        this.totalShops = totalShops;
        this.totalCommodities = totalCommodities;
    }

    public Integer getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Integer getTotalShops() {
        return totalShops;
    }

    public void setTotalShops(Integer totalShops) {
        this.totalShops = totalShops;
    }

    public Integer getTotalCommodities() {
        return totalCommodities;
    }

    public void setTotalCommodities(Integer totalCommodities) {
        this.totalCommodities = totalCommodities;
    }
}
