package com.zl.way.user.model;

public class UserProfileBo extends UserProfile {

    private Long userLoginId;
    private String userNickName;
    private String userTel;

    @Override
    public Long getUserLoginId() {
        return userLoginId;
    }

    @Override
    public void setUserLoginId(Long userLoginId) {
        this.userLoginId = userLoginId;
    }

    @Override
    public String getUserNickName() {
        return userNickName;
    }

    @Override
    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
