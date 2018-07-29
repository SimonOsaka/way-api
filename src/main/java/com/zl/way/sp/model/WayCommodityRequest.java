package com.zl.way.sp.model;

import com.zl.way.util.PageParam;

import java.math.BigDecimal;
import java.util.List;

public class WayCommodityRequest extends PageParam {

    private Long id;

    private Long shopId;

    private String name;

    private BigDecimal price;

    private List<String> imgUrlList;

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

}
