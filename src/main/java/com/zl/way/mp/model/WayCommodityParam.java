package com.zl.way.mp.model;

import java.util.List;

public class WayCommodityParam extends WayCommodity {

    private List<String> imgUrlList;

    private Byte status;

    private String rejectContent;

    public List<String> getImgUrlList() {

        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {

        this.imgUrlList = imgUrlList;
    }

    public Byte getStatus() {

        return status;
    }

    public void setStatus(Byte status) {

        this.status = status;
    }

    public String getRejectContent() {

        return rejectContent;
    }

    public void setRejectContent(String rejectContent) {

        this.rejectContent = rejectContent;
    }
}
