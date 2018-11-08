package com.zl.way.user.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserDevice implements Serializable {

    private Long userLoginId;

    private String deviceToken;

    private String jpushRegId;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getUserLoginId() {

        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {

        this.userLoginId = userLoginId;
    }

    public String getDeviceToken() {

        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {

        this.deviceToken = deviceToken == null ? null : deviceToken.trim();
    }

    public String getJpushRegId() {

        return jpushRegId;
    }

    public void setJpushRegId(String jpushRegId) {

        this.jpushRegId = jpushRegId == null ? null : jpushRegId.trim();
    }

    public BigDecimal getLatitude() {

        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {

        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {

        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {

        this.longitude = longitude;
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