package com.zl.way.amap.api.model;

import java.util.List;

public class AMapInputTipsResponse {

    private int code;

    private String msg;

    List<AMapInputTipsModel> aMapInputTipsModelList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AMapInputTipsModel> getaMapInputTipsModelList() {
        return aMapInputTipsModelList;
    }

    public void setaMapInputTipsModelList(List<AMapInputTipsModel> aMapInputTipsModelList) {
        this.aMapInputTipsModelList = aMapInputTipsModelList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
