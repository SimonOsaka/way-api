package com.zl.way.sp.model;

import java.util.List;

public class WayCommodityCondition extends WayCommodity {
    private List<Integer> abstractIdList;
    private Long excludeId;

    public void setAbstractIdList(List<Integer> abstractIdList) {
        this.abstractIdList = abstractIdList;
    }

    public List<Integer> getAbstractIdList() {
        return abstractIdList;
    }

    public void setExcludeId(Long excludeId) {
        this.excludeId = excludeId;
    }

    public Long getExcludeId() {
        return excludeId;
    }
}
