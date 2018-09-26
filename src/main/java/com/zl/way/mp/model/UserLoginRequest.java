package com.zl.way.mp.model;

import com.zl.way.util.PageParam;

public class UserLoginRequest extends PageParam {

    private Long id;

    private String loginTel;

    private String loginName;

    public String getLoginTel() {

        return loginTel;
    }

    public void setLoginTel(String loginTel) {

        this.loginTel = loginTel;
    }

    public String getLoginName() {

        return loginName;
    }

    public void setLoginName(String loginName) {

        this.loginName = loginName;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }
}
