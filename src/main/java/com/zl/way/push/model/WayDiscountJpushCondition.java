package com.zl.way.push.model;

import java.math.BigDecimal;
import java.util.Date;

public class WayDiscountJpushCondition {

    private BigDecimal radius;
    private Date startTime;
    private Date endTime;

    public BigDecimal getRadius() {
        return radius;
    }

    public void setRadius(BigDecimal radius) {
        this.radius = radius;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}