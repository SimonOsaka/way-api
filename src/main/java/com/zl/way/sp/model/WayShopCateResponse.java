package com.zl.way.sp.model;

import java.util.List;

public class WayShopCateResponse {

    List<WayShopCateRootBo> cateRootBoList;

    List<WayShopCateLeafBo> cateLeafBoList;

    public List<WayShopCateRootBo> getCateRootBoList() {

        return cateRootBoList;
    }

    public void setCateRootBoList(List<WayShopCateRootBo> cateRootBoList) {

        this.cateRootBoList = cateRootBoList;
    }

    public List<WayShopCateLeafBo> getCateLeafBoList() {

        return cateLeafBoList;
    }

    public void setCateLeafBoList(List<WayShopCateLeafBo> cateLeafBoList) {

        this.cateLeafBoList = cateLeafBoList;
    }
}
