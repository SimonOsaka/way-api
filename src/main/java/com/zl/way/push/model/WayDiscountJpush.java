package com.zl.way.push.model;

import java.io.Serializable;
import java.util.Date;

public class WayDiscountJpush implements Serializable {
    private Long id;

    private Long discountId;

    private Byte hasPushed;

    private Date pushedTime;

    private Date createTime;


    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Byte getHasPushed() {
        return hasPushed;
    }

    public void setHasPushed(Byte hasPushed) {
        this.hasPushed = hasPushed;
    }

    public Date getPushedTime() {
        return pushedTime;
    }

    public void setPushedTime(Date pushedTime) {
        this.pushedTime = pushedTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}