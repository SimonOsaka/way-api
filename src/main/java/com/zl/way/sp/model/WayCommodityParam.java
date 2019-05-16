package com.zl.way.sp.model;

import java.util.List;

public class WayCommodityParam extends WayCommodity {

    private List<String> imgUrlList;
    private List<Integer> abstractWordIdList;

    public List<String> getImgUrlList() {

        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {

        this.imgUrlList = imgUrlList;
    }

    public List<Integer> getAbstractWordIdList() {
        return abstractWordIdList;
    }

    public void setAbstractWordIdList(List<Integer> abstractWordIdList) {
        this.abstractWordIdList = abstractWordIdList;
    }
}
