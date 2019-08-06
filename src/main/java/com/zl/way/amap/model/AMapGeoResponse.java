package com.zl.way.amap.model;

import java.util.List;

public class AMapGeoResponse {

    private int code;
    private String msg;
    private List<AMapGeoModel> aMapGeoModelList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AMapGeoModel> getaMapGeoModelList() {
        return aMapGeoModelList;
    }

    public void setaMapGeoModelList(List<AMapGeoModel> aMapGeoModelList) {
        this.aMapGeoModelList = aMapGeoModelList;
    }
}
