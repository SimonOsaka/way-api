package com.zl.way.mp.api.model;

import java.util.List;

import com.zl.way.mp.model.WayCommodityAbstractWordRelationshipBo;

public class WayCommodityAbstractWordRelationshipResponse {
    private List<WayCommodityAbstractWordRelationshipBo> abstractWordRelationshipBoList;

    public List<WayCommodityAbstractWordRelationshipBo> getAbstractWordRelationshipBoList() {
        return abstractWordRelationshipBoList;
    }

    public void
        setAbstractWordRelationshipBoList(List<WayCommodityAbstractWordRelationshipBo> abstractWordRelationshipBoList) {
        this.abstractWordRelationshipBoList = abstractWordRelationshipBoList;
    }
}