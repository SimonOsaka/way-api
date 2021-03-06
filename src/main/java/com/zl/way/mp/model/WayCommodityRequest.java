package com.zl.way.mp.model;

import java.math.BigDecimal;
import java.util.List;

import com.zl.way.util.PageParam;

public class WayCommodityRequest extends PageParam {

    private Long id;

    private Long shopId;

    private String name;

    private BigDecimal price;

    private List<String> imgUrlList;

    private Byte status;

    private String rejectContent;

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

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BigDecimal getPrice() {

        return price;
    }

    public void setPrice(BigDecimal price) {

        this.price = price;
    }

    public List<String> getImgUrlList() {

        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {

        this.imgUrlList = imgUrlList;
    }

    public Byte getStatus() {

        return status;
    }

    public void setStatus(Byte status) {

        this.status = status;
    }

    public String getRejectContent() {

        return rejectContent;
    }

    public void setRejectContent(String rejectContent) {

        this.rejectContent = rejectContent;
    }
}
