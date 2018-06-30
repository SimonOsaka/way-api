package com.zl.way.city.model;

import java.io.Serializable;
import java.util.Date;


public class WayCity implements Serializable {

    private Integer id;


    private String name;


    private String adcode;


    private String citycode;


    private Byte isUsed;


    private Date createTime;


    private Date updateTime;


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getAdcode() {
        return adcode;
    }


    public void setAdcode(String adcode) {
        this.adcode = adcode == null ? null : adcode.trim();
    }


    public String getCitycode() {
        return citycode;
    }


    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }


    public Byte getIsUsed() {
        return isUsed;
    }


    public void setIsUsed(Byte isUsed) {
        this.isUsed = isUsed;
    }


    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Date getUpdateTime() {
        return updateTime;
    }


    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}