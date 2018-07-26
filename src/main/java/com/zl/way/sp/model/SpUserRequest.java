package com.zl.way.sp.model;

import com.zl.way.util.PageParam;

public class SpUserRequest extends PageParam {

    private Long userLoginId;

    private String userLoginName;

    private String userLoginPassword;

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