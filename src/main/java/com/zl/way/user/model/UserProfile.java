package com.zl.way.user.model;

import java.math.BigDecimal;
import java.util.Date;

public class UserProfile {

    private Long id;

    private Long userLoginId;

    private String userNickName;

    private Date createTime;

    private Date updateTime;

    private String addressName;

    private BigDecimal addressLongitude;

    private BigDecimal addressLatitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName == null ? null : userNickName.trim();
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

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public BigDecimal getAddressLongitude() {
        return addressLongitude;
    }

    public void setAddressLongitude(BigDecimal addressLongitude) {
        this.addressLongitude = addressLongitude;
    }

    public BigDecimal getAddressLatitude() {
        return addressLatitude;
    }

    public void setAddressLatitude(BigDecimal addressLatitude) {
        this.addressLatitude = addressLatitude;
    }
}