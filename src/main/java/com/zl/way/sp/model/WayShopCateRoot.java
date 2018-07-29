package com.zl.way.sp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WayShopCateRoot implements Serializable {

    private Integer id;

    private String cateName;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private List<WayShopCateLeaf> leafList;

    private static final long serialVersionUID = 1L;

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {

        this.id = id;
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

    public List<WayShopCateLeaf> getLeafList() {

        return leafList;
    }

    public void setLeafList(List<WayShopCateLeaf> leafList) {

        this.leafList = leafList;
    }
}