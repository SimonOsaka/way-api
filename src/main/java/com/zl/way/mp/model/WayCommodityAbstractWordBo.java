package com.zl.way.mp.model;

import java.util.List;

public class WayCommodityAbstractWordBo extends WayCommodityAbstractWord {
    private List<WayCommodityAbstractWord> commodityAbstractWordList;

    public List<WayCommodityAbstractWord> getCommodityAbstractWordList() {
        return commodityAbstractWordList;
    }

    public void setCommodityAbstractWordList(List<WayCommodityAbstractWord> commodityAbstractWordList) {
        this.commodityAbstractWordList = commodityAbstractWordList;
    }

}
