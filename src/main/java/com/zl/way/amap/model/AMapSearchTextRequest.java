package com.zl.way.amap.model;

import java.util.List;

public class AMapSearchTextRequest {

    private String keywords;

    private String city;

    private List<String> typeList;

    private Integer page;

    private Integer offset;

    public String getKeywords() {

        return keywords;
    }

    public void setKeywords(String keywords) {

        this.keywords = keywords;
    }

    public String getCity() {

        return city;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public List<String> getTypeList() {

        return typeList;
    }

    public void setTypeList(List<String> typeList) {

        this.typeList = typeList;
    }

    public Integer getPage() {

        return page;
    }

    public void setPage(Integer page) {

        this.page = page;
    }

    public Integer getOffset() {

        return offset;
    }

    public void setOffset(Integer offset) {

        this.offset = offset;
    }
}
