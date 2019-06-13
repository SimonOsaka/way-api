package com.zl.way.commodity.model;

import java.io.Serializable;
import java.util.Date;

public class WayCommodityAbstractWordRelationship implements Serializable {
    private Integer id;

    private Integer abstractWordId;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private String abstractWordIds;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAbstractWordId() {
        return abstractWordId;
    }

    public void setAbstractWordId(Integer abstractWordId) {
        this.abstractWordId = abstractWordId;
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

    public String getAbstractWordIds() {
        return abstractWordIds;
    }

    public void setAbstractWordIds(String abstractWordIds) {
        this.abstractWordIds = abstractWordIds == null ? null : abstractWordIds.trim();
    }
}