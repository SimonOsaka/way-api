package com.zl.way.user.api.model;

public class UserResponse {

    private String userTel;
    private String userValidCode;
    private String userNickName;
    private Long userLoginId;
    private String token;

    ////////////

    private String userAgreementsUrl;

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserValidCode() {
        return userValidCode;
    }

    public void setUserValidCode(String userValidCode) {
        this.userValidCode = userValidCode;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public Long getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserAgreementsUrl() {
        return userAgreementsUrl;
    }

    public void setUserAgreementsUrl(String userAgreementsUrl) {
        this.userAgreementsUrl = userAgreementsUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
