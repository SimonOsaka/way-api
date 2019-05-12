package com.zl.way.sp.model;

public class WayCommodityAbstractWordCondition extends WayCommodityAbstractWord {
    private Integer pid;
    private Integer pathPid;

    public Integer getPathPid() {
        return pathPid;
    }

    public void setPathPid(Integer pathPid) {
        this.pathPid = pathPid;
    }

    @Override public Integer getPid() {
        return pid;
    }

    @Override public void setPid(Integer pid) {
        this.pid = pid;
    }
}
