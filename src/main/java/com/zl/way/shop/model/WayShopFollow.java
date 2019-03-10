package com.zl.way.shop.model;

import java.io.Serializable;
import java.util.Date;

public class WayShopFollow implements Serializable {

    private Long id;

    private Long shopId;

    private Long userLoginId;

    private Byte hasFollowed;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getShopId() {

        return shopId;
    }

    public void setShopId(Long shopId) {

        this.shopId = shopId;
    }

    public Long getUserLoginId() {

        return userLoginId;
    }

    public void setUserLoginId(Long userLoginId) {

        this.userLoginId = userLoginId;
    }

    public Byte getHasFollowed() {

        return hasFollowed;
    }

    public void setHasFollowed(Byte hasFollowed) {

        this.hasFollowed = hasFollowed;
    }

    public Byte getIsDeleted() {

        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {

        this.isDeleted = isDeleted;
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