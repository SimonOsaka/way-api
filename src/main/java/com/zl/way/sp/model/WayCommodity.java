package com.zl.way.sp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class WayCommodity implements Serializable {

    private Long id;

    private String name;

    private BigDecimal price;

    private String imgUrl;

    private String imgUrl1;

    private String imgUrl2;

    private String imgUrl3;

    private String imgUrl4;

    private Long shopId;

    private Date createTime;

    private Date updateTime;

    private Byte isDeleted;

    private String namePinyin;

    private String namePy;

    private Integer abstractWordId;

    private List<WayCommodityLog> commodityLogList;

    private static final long serialVersionUID = 1L;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public String getImgUrl() {

        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {

        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getImgUrl1() {

        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {

        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {

        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {

        this.imgUrl2 = imgUrl2;
    }

    public String getImgUrl3() {

        return imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {

        this.imgUrl3 = imgUrl3;
    }

    public String getImgUrl4() {

        return imgUrl4;
    }

    public void setImgUrl4(String imgUrl4) {

        this.imgUrl4 = imgUrl4;
    }

    public Long getShopId() {

        return shopId;
    }

    public void setShopId(Long shopId) {

        this.shopId = shopId;
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

    public String getNamePinyin() {

        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {

        this.namePinyin = namePinyin == null ? null : namePinyin.trim();
    }

    public String getNamePy() {

        return namePy;
    }

    public void setNamePy(String namePy) {

        this.namePy = namePy == null ? null : namePy.trim();
    }

    public Integer getAbstractWordId() {
        return abstractWordId;
    }

    public void setAbstractWordId(Integer abstractWordId) {
        this.abstractWordId = abstractWordId;
    }

    public List<WayCommodityLog> getCommodityLogList() {

        return commodityLogList;
    }

    public void setCommodityLogList(List<WayCommodityLog> commodityLogList) {

        this.commodityLogList = commodityLogList;
    }
}