package com.zl.way.sp.model;

import com.zl.way.util.PageParam;

public class WayCommodityAbstractWordRequest extends PageParam {
    private Integer id;
    private Integer shopCateLeafId;
    private Integer pid;
    private Integer pathPid;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPathPid() {
        return pathPid;
    }

    public void setPathPid(Integer pathPid) {
        this.pathPid = pathPid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getShopCateLeafId() {
        return shopCateLeafId;
    }

    public void setShopCateLeafId(Integer shopCateLeafId) {
        this.shopCateLeafId = shopCateLeafId;
    }
}
