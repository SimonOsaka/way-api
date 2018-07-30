package com.zl.way.sp.api.validation;

import com.zl.way.sp.model.WayShopRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class WayShopApiValidation {

    private WayShopRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public WayShopApiValidation(WayShopRequest request) {

        this.request = request;
    }

    public WayShopApiValidation shopId() {

        if (null == request.getId()) {
            validationMessageList.add("商家id不能为空");
        }
        return this;
    }

    public WayShopApiValidation shopCateLeafId() {

        if (null == request.getShopCateLeafId() || request.getShopCateLeafId() <= 0) {
            validationMessageList.add("商家分类不能为空");
        }
        return this;
    }

    public WayShopApiValidation shopName() {

        if (StringUtils.isBlank(request.getShopName())) {
            validationMessageList.add("商家名称不能为空");
        }
        return this;
    }

    public WayShopApiValidation shopAddress() {

        if (StringUtils.isBlank(request.getShopAddress())) {
            validationMessageList.add("商家地址不能为空");
        }
        return this;
    }

    public WayShopApiValidation shopBusinessTime() {

        if (StringUtils.isBlank(request.getShopBusinessTime1())) {
            validationMessageList.add("商家营业时间1不能为空");
        }
        return this;
    }

    public WayShopApiValidation shopLogoUrl() {

        if (StringUtils.isBlank(request.getShopLogoUrl())) {
            validationMessageList.add("商家LOGO不能为空");
        }
        return this;
    }

    public WayShopApiValidation shopTel() {

        if (StringUtils.isBlank(request.getShopTel())) {
            validationMessageList.add("商家电话不能为空");
            return this;
        }

        char[] telCharArr = request.getShopTel().toCharArray();
        for (char telChar : telCharArr) {
            if (telChar != '1' && telChar != '2' && telChar != '3' && telChar != '4'
                    && telChar != '5' && telChar != '6' && telChar != '7' && telChar != '8'
                    && telChar != '9' && telChar != '0' && telChar != '-') {
                validationMessageList.add("商家电话必须数字和-");
                return this;
            }
        }
        return this;
    }

    public WayShopApiValidation shopLocation() {

        if (null == request.getShopLat() || null == request.getShopLng()) {
            validationMessageList.add("商家地址不能为空[必须选择地址]");
        }
        return this;
    }

    public boolean hasErrors() {

        return !validationMessageList.isEmpty();
    }

    public List<String> getErrors() {

        return validationMessageList;
    }

    public static void main(String[] args) {

        WayShopRequest request = new WayShopRequest();
        request.setShopTel("33234234-");
        System.out.println(new WayShopApiValidation(request).shopTel().hasErrors());
    }
}
