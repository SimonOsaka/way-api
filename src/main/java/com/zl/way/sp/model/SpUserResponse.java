package com.zl.way.sp.model;

import com.zl.way.user.model.UserProfile;

public class SpUserResponse {

    private String token;

    private Long userLoginId;

    private UserProfile profile;

    private WayShopBo shop;

    public String getToken() {

        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public Long getUserLoginId() {

        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {

        this.userLoginId = userLoginId;
    }

    public UserProfile getProfile() {

        return profile;
    }

    public void setProfile(UserProfile profile) {

        this.profile = profile;
    }

    public WayShopBo getShop() {

        return shop;
    }

    public void setShop(WayShopBo shop) {

        this.shop = shop;
    }
}