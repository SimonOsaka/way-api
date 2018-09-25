package com.zl.way.mp.api.validation;

import com.zl.way.mp.enums.WayShopStatusEnum;
import com.zl.way.mp.model.WayShopRequest;
import com.zl.way.util.SensiWordsUtil;
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

    public WayShopApiValidation shopStatus() {

        if (null == request.getShopStatus()) {
            validationMessageList.add("商家状态不能为空");
            return this;
        }

        WayShopStatusEnum shopStatusEnum = WayShopStatusEnum.getStatus(request.getShopStatus());
        if (null == shopStatusEnum) {
            validationMessageList.add("商家状态不正确");
            return this;
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
            return this;
        }

        if (SensiWordsUtil.isSensiWords(request.getShopName())) {
            String sensiWords = SensiWordsUtil.getSensiWords(request.getShopName());
            validationMessageList.add("商家名称包含敏感词汇[" + sensiWords + "]");
            return this;
        }

        if (StringUtils.length(request.getShopName()) > 20) {
            validationMessageList.add("商家名称最多20字");
        }
        return this;
    }

    public WayShopApiValidation shopAddress() {

        if (StringUtils.isBlank(request.getShopAddress())) {
            validationMessageList.add("商家地址不能为空");
            return this;
        }

        if (SensiWordsUtil.isSensiWords(request.getShopAddress())) {
            String sensiWords = SensiWordsUtil.getSensiWords(request.getShopAddress());
            validationMessageList.add("商家地址包含敏感词汇[" + sensiWords + "]");
            return this;
        }

        if (StringUtils.length(request.getShopAddress()) > 200) {
            validationMessageList.add("商家地址最多200字");
        }
        return this;
    }

    public WayShopApiValidation shopBusinessTime1() {

        if (StringUtils.isBlank(request.getShopBusinessTime1())) {
            validationMessageList.add("商家营业时间1不能为空");
            return this;
        }

        if (SensiWordsUtil.isSensiWords(request.getShopBusinessTime1())) {
            String sensiWords = SensiWordsUtil.getSensiWords(request.getShopBusinessTime1());
            validationMessageList.add("商家营业时间1包含敏感词汇[" + sensiWords + "]");
            return this;
        }

        if (StringUtils.length(request.getShopBusinessTime1()) > 11) {
            validationMessageList.add("商家营业时间1格式不正确");
        }
        return this;
    }

    public WayShopApiValidation shopLogoUrl() {

        if (StringUtils.isBlank(request.getShopLogoUrl())) {
            validationMessageList.add("商家LOGO不能为空");
            return this;
        }

        if (StringUtils.length(request.getShopLogoUrl()) > 300) {
            validationMessageList.add("商家LOGO格式不正确");
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

    public WayShopApiValidation shopInfo() {

        if (StringUtils.isNotBlank(request.getShopInfo())) {
            if (SensiWordsUtil.isSensiWords(request.getShopInfo())) {
                String sensiWords = SensiWordsUtil.getSensiWords(request.getShopInfo());
                validationMessageList.add("商家简介包含敏感词汇[" + sensiWords + "]");
                return this;
            }

            if (StringUtils.length(request.getShopInfo()) > 100) {
                validationMessageList.add("商家简介最多100字");
                return this;
            }
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
