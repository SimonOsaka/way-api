package com.zl.way.mp.model;

import java.util.List;
import java.util.Map;

public class WayCommodityAbstractWordBo extends WayCommodityAbstractWord {
    private List<WayCommodityAbstractWord> commodityAbstractWordList;
    private List<Map<String, Object>> abstractWordMapList;

    public List<WayCommodityAbstractWord> getCommodityAbstractWordList() {
        return commodityAbstractWordList;
    }

    public void setCommodityAbstractWordList(List<WayCommodityAbstractWord> commodityAbstractWordList) {
        this.commodityAbstractWordList = commodityAbstractWordList;
    }

    public List<Map<String, Object>> getAbstractWordMapList() {
        return abstractWordMapList;
    }

    public void setAbstractWordMapList(List<Map<String, Object>> abstractWordMapList) {
        this.abstractWordMapList = abstractWordMapList;
    }
}
