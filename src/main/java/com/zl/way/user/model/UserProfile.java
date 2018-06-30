package com.zl.way.user.model;

import java.util.Date;

public class UserProfile {

    private Long id;


    private Long userLoginId;


    private String userNickName;


    private Date createTime;


    private Date updateTime;


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
}