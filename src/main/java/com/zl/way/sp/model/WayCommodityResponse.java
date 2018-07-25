package com.zl.way.sp.model;

import java.util.List;

public class WayCommodityResponse {

    private WayCommodityBo commodityBo;

    private List<WayCommodityBo> commodityBoList;

    public WayCommodityBo getCommodityBo() {

        return commodityBo;
    }

    public void setCommodityBo(WayCommodityBo commodityBo) {

        this.commodityBo = commodityBo;
    }

    public List<WayCommodityBo> getCommodityBoList() {

        return commodityBoList;
    }

    public void setCommodityBoList(List<WayCommodityBo> commodityBoList) {

        this.commodityBoList = commodityBoList;
    }
}
