package com.zl.way.amap.model;

import java.util.List;

public class AMapDistrictResponse {

    private int code;
    private String msg;
    private List<AMapDistrictModel> aMapDistrictModelList;

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

    public List<AMapDistrictModel> getaMapDistrictModelList() {
        return aMapDistrictModelList;
    }

    public void setaMapDistrictModelList(List<AMapDistrictModel> aMapDistrictModelList) {
        this.aMapDistrictModelList = aMapDistrictModelList;
    }
}
