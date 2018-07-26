package com.zl.way.amap.model;

import java.util.List;

public class AMapSearchTextResponse {

    private int code;

    private String msg;

    private List<AMapSearchTextModel> searchTextModelList;

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {

        this.msg = msg;
    }

    public List<AMapSearchTextModel> getSearchTextModelList() {

        return searchTextModelList;
    }

    public void setSearchTextModelList(List<AMapSearchTextModel> searchTextModelList) {

        this.searchTextModelList = searchTextModelList;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {

        this.code = code;
    }
}
