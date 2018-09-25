package com.zl.way.mp.model;

import java.io.Serializable;
import java.util.Date;

public class UserLogin implements Serializable {
    private Long id;

    private String loginTel;

    private String validCode;

    private Date validCodeExpire;

    private Date createTime;

    private Date updateTime;

    private Byte isUsed;

    private Byte isDeleted;

    private Date loginTime;

    private Date logoutTime;

    private String loginName;

    private String loginPassword;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginTel() {
        return loginTel;
    }

    public void setLoginTel(String loginTel) {
        this.loginTel = loginTel == null ? null : loginTel.trim();
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode == null ? null : validCode.trim();
    }

    public Date getValidCodeExpire() {
        return validCodeExpire;
    }

    public void setValidCodeExpire(Date validCodeExpire) {
        this.validCodeExpire = validCodeExpire;
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

    public Byte getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Byte isUsed) {
        this.isUsed = isUsed;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword == null ? null : loginPassword.trim();
    }
}