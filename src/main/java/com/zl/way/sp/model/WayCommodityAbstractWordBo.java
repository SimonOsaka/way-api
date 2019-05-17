package com.zl.way.sp.model;

import java.util.List;

public class WayCommodityAbstractWordBo extends WayCommodityAbstractWord {
    private String abstractWordPathName;
    private List<WayCommodityAbstractWordBo> commodityAbstractWordBoList;

    public String getAbstractWordPathName() {
        return abstractWordPathName;
    }

    public void setAbstractWordPathName(String abstractWordPathName) {
        this.abstractWordPathName = abstractWordPathName;
    }

    public List<WayCommodityAbstractWordBo> getCommodityAbstractWordBoList() {
        return commodityAbstractWordBoList;
    }

    public void setCommodityAbstractWordBoList(List<WayCommodityAbstractWordBo> commodityAbstractWordBoList) {
        this.commodityAbstractWordBoList = commodityAbstractWordBoList;
    }
}
