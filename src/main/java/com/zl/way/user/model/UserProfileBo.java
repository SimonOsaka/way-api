package com.zl.way.user.model;

public class UserProfileBo extends UserProfile {

    private Long userLoginId;
    private String userNickName;

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
}
