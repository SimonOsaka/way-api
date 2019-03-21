package com.zl.way.amap.model;

import java.util.List;

public class AMapConvertResponse {
    private int code;
    private String msg;
    private List<AMapConvertModel> convertModelList;

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

    public List<AMapConvertModel> getConvertModelList() {
        return convertModelList;
    }

    public void setConvertModelList(List<AMapConvertModel> convertModelList) {
        this.convertModelList = convertModelList;
    }
}
