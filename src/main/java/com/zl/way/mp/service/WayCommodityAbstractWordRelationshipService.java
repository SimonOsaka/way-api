package com.zl.way.mp.service;

import java.util.List;

import com.zl.way.mp.model.WayCommodityAbstractWordRelationshipBo;
import com.zl.way.mp.model.WayCommodityAbstractWordRelationshipParam;
import com.zl.way.util.PageParam;

public interface WayCommodityAbstractWordRelationshipService {
    List<WayCommodityAbstractWordRelationshipBo>
        queryAbstractRelationShip(WayCommodityAbstractWordRelationshipParam params, PageParam pageParam);

    void saveAbstractWordRelationship(WayCommodityAbstractWordRelationshipParam params);

}
