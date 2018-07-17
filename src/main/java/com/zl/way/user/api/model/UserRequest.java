package com.zl.way.user.api.model;

public class UserRequest {

    private String userTel;

    private String validCode;

    private Long userLoginId;

    private String userLoginName;

    private String userLoginPassword;

    public String getUserTel() {

        return userTel;
    }

    public void setUserTel(String userTel) {

        this.userTel = userTel;
    }

    public String getValidCode() {

        return validCode;
    }

    public void setValidCode(String validCode) {

        this.validCode = validCode;
    }

    public Long getUserLoginId() {

        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {

        this.userLoginId = userLoginId;
    }

    public String getUserLoginName() {

        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {

        this.userLoginName = userLoginName;
    }

    public String getUserLoginPassword() {

        return userLoginPassword;
    }

    public void setUserLoginPassword(String userLoginPassword) {

        this.userLoginPassword = userLoginPassword;
    }
}
