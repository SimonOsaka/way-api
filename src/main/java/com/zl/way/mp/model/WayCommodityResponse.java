package com.zl.way.mp.model;

import java.util.List;
import java.util.Map;

public class WayCommodityResponse {

    private WayCommodityBo commodityBo;

    private List<WayCommodityBo> commodityBoList;

    private Map<String, String> commodityStatusMap;

    private Long commodityTotal;

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

    public Map<String, String> getCommodityStatusMap() {

        return commodityStatusMap;
    }

    public void setCommodityStatusMap(Map<String, String> commodityStatusMap) {

        this.commodityStatusMap = commodityStatusMap;
    }

    public Long getCommodityTotal() {

        return commodityTotal;
    }

    public void setCommodityTotal(Long commodityTotal) {

        this.commodityTotal = commodityTotal;
    }
}
