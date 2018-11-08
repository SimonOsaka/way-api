package com.zl.way.user.api.model;

import java.math.BigDecimal;

public class UserDeviceRequest {

    private Long userLoginId;

    private String deviceToken;

    private String jpushRegId;

    private BigDecimal latitude;

    private BigDecimal longitude;

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

        this.deviceToken = deviceToken;
    }

    public String getJpushRegId() {

        return jpushRegId;
    }

    public void setJpushRegId(String jpushRegId) {

        this.jpushRegId = jpushRegId;
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
}