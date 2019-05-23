package com.zl.way.mp.model;

import java.util.List;

public class WayCommodityAbstractWordRelationshipParam extends WayCommodityAbstractWordRelationship {
    private List<Integer> abstractWordIdList;

    public List<Integer> getAbstractWordIdList() {
        return abstractWordIdList;
    }

    public void setAbstractWordIdList(List<Integer> abstractWordIdList) {
        this.abstractWordIdList = abstractWordIdList;
    }
}