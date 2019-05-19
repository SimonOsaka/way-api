package com.zl.way.amap.model;

import java.util.List;

public class AMapAroundResponse {

    private int code;
    private String msg;
    private List<AMapAroundModel> aMapAroundModelList;

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

    public List<AMapAroundModel> getaMapAroundModelList() {
        return aMapAroundModelList;
    }

    public void setaMapAroundModelList(List<AMapAroundModel> aMapAroundModelList) {
        this.aMapAroundModelList = aMapAroundModelList;
    }
}
