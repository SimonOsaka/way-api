package com.zl.way.mp.model;

import java.util.List;

public class UserLoginResponse {

    private List<UserLoginBo> userLoginBoList;

    private Long userLoginTotal;

    public List<UserLoginBo> getUserLoginBoList() {

        return userLoginBoList;
    }

    public void setUserLoginBoList(List<UserLoginBo> userLoginBoList) {

        this.userLoginBoList = userLoginBoList;
    }

    public Long getUserLoginTotal() {

        return userLoginTotal;
    }

    public void setUserLoginTotal(Long userLoginTotal) {

        this.userLoginTotal = userLoginTotal;
    }
}
