package com.zl.way.commodity.mapper.model;

import java.util.List;

import com.zl.way.commodity.model.WayCommodityAbstractWordRelationship;

public class WayCommodityAbstractWordRelationshipCondition extends WayCommodityAbstractWordRelationship {
    private List<Integer> abstractWordIdList;

    public List<Integer> getAbstractWordIdList() {
        return abstractWordIdList;
    }

    public void setAbstractWordIdList(List<Integer> abstractWordIdList) {
        this.abstractWordIdList = abstractWordIdList;
    }
}