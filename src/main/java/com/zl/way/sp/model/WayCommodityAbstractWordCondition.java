package com.zl.way.sp.model;

import java.util.List;

public class WayCommodityAbstractWordCondition extends WayCommodityAbstractWord {
    private Integer pid;
    private Integer pathPid;
    private List<Integer> ids;

    public Integer getPathPid() {
        return pathPid;
    }

    public void setPathPid(Integer pathPid) {
        this.pathPid = pathPid;
    }

    @Override
    public Integer getPid() {
        return pid;
    }

    @Override
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
