package com.zl.way.sp.model;

import com.zl.way.sp.enums.WayCommodityStatusEnum;
import com.zl.way.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class WayCommodityBo extends WayCommodity {

    private List<String> imgUrlList;

    private String statusName;

    private String abstractWordName;

    public List<String> getImgUrlList() {

        return imgUrlList;
    }

    public void setImgUrlList(List<String> imgUrlList) {

        this.imgUrlList = imgUrlList;
    }

    public String getStatusName() {

        if (StringUtils.isNotBlank(statusName)) {
            return statusName;
        }

        WayCommodityStatusEnum currentCommodityStatus =
            EnumUtil.getEnumByValue(getIsDeleted(), WayCommodityStatusEnum.class);
        if (null != currentCommodityStatus) {
            return currentCommodityStatus.getDesc();
        }
        return null;
    }

    public void setStatusName(String statusName) {

        this.statusName = statusName;
    }

    public String getAbstractWordName() {
        return abstractWordName;
    }

    public void setAbstractWordName(String abstractWordName) {
        this.abstractWordName = abstractWordName;
    }
}
