package com.zl.way.mp.model;

import java.io.Serializable;
import java.util.Date;

public class WayShopQualification implements Serializable {
    private Long id;

    private Long shopId;

    private String idcardFrontImgUrl;

    private String idcardBackImgUrl;

    private String idcardHandImgUrl;

    private String businessLicenseImgUrl;

    private String shopOutsideImgUrl;

    private String shopInsideImgUrl;

    private String other1ImgUrl;

    private String other2ImgUrl;

    private String other3ImgUrl;

    private String other4ImgUrl;

    private String other5ImgUrl;

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

    public String getIdcardFrontImgUrl() {
        return idcardFrontImgUrl;
    }

    public void setIdcardFrontImgUrl(String idcardFrontImgUrl) {
        this.idcardFrontImgUrl = idcardFrontImgUrl == null ? null : idcardFrontImgUrl.trim();
    }

    public String getIdcardBackImgUrl() {
        return idcardBackImgUrl;
    }

    public void setIdcardBackImgUrl(String idcardBackImgUrl) {
        this.idcardBackImgUrl = idcardBackImgUrl == null ? null : idcardBackImgUrl.trim();
    }

    public String getIdcardHandImgUrl() {
        return idcardHandImgUrl;
    }

    public void setIdcardHandImgUrl(String idcardHandImgUrl) {
        this.idcardHandImgUrl = idcardHandImgUrl == null ? null : idcardHandImgUrl.trim();
    }

    public String getBusinessLicenseImgUrl() {
        return businessLicenseImgUrl;
    }

    public void setBusinessLicenseImgUrl(String businessLicenseImgUrl) {
        this.businessLicenseImgUrl = businessLicenseImgUrl == null ? null : businessLicenseImgUrl.trim();
    }

    public String getShopOutsideImgUrl() {
        return shopOutsideImgUrl;
    }

    public void setShopOutsideImgUrl(String shopOutsideImgUrl) {
        this.shopOutsideImgUrl = shopOutsideImgUrl == null ? null : shopOutsideImgUrl.trim();
    }

    public String getShopInsideImgUrl() {
        return shopInsideImgUrl;
    }

    public void setShopInsideImgUrl(String shopInsideImgUrl) {
        this.shopInsideImgUrl = shopInsideImgUrl == null ? null : shopInsideImgUrl.trim();
    }

    public String getOther1ImgUrl() {
        return other1ImgUrl;
    }

    public void setOther1ImgUrl(String other1ImgUrl) {
        this.other1ImgUrl = other1ImgUrl == null ? null : other1ImgUrl.trim();
    }

    public String getOther2ImgUrl() {
        return other2ImgUrl;
    }

    public void setOther2ImgUrl(String other2ImgUrl) {
        this.other2ImgUrl = other2ImgUrl == null ? null : other2ImgUrl.trim();
    }

    public String getOther3ImgUrl() {
        return other3ImgUrl;
    }

    public void setOther3ImgUrl(String other3ImgUrl) {
        this.other3ImgUrl = other3ImgUrl == null ? null : other3ImgUrl.trim();
    }

    public String getOther4ImgUrl() {
        return other4ImgUrl;
    }

    public void setOther4ImgUrl(String other4ImgUrl) {
        this.other4ImgUrl = other4ImgUrl == null ? null : other4ImgUrl.trim();
    }

    public String getOther5ImgUrl() {
        return other5ImgUrl;
    }

    public void setOther5ImgUrl(String other5ImgUrl) {
        this.other5ImgUrl = other5ImgUrl == null ? null : other5ImgUrl.trim();
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