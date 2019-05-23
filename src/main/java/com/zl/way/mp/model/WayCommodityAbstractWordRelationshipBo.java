package com.zl.way.mp.model;

import java.util.List;
import java.util.Map;

public class WayCommodityAbstractWordRelationshipBo extends WayCommodityAbstractWordRelationship {
    private List<Map<String, Object>> abstractWordRelationshipList;

    public List<Map<String, Object>> getAbstractWordRelationshipList() {
        return abstractWordRelationshipList;
    }

    public void setAbstractWordRelationshipList(List<Map<String, Object>> abstractWordRelationshipList) {
        this.abstractWordRelationshipList = abstractWordRelationshipList;
    }
}