package com.zl.way.sp.model;

import java.io.Serializable;
import java.util.Date;

public class WayShopCateLeaf implements Serializable {
    private Integer id;

    private Integer shopCateRootId;

    private String cateName;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopCateRootId() {
        return shopCateRootId;
    }

    public void setShopCateRootId(Integer shopCateRootId) {
        this.shopCateRootId = shopCateRootId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName == null ? null : cateName.trim();
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

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}