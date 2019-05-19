package com.zl.way.mp.api.validation;

import com.zl.way.mp.api.model.SpUserShopReq;

import java.util.ArrayList;
import java.util.List;

public class SpUserShopApiValidation {

    private SpUserShopReq request;

    private List<String> validationMessageList = new ArrayList<>();

    public SpUserShopApiValidation(SpUserShopReq request) {

        this.request = request;
    }

    public SpUserShopApiValidation id() {

        if (null == request.getId()) {
            validationMessageList.add("用户商家id不能为空");
            return this;
        }

        if (request.getId() < 1 || request.getId() > Long.MAX_VALUE) {
            validationMessageList.add("用户商家id值不正确");
            return this;
        }
        return this;
    }

    public SpUserShopApiValidation userId() {
        if (null == request.getUserLoginId()) {
            validationMessageList.add("用户id不能为空");
            return this;
        }

        if (request.getUserLoginId() < 1 || request.getUserLoginId() > Long.MAX_VALUE) {
            validationMessageList.add("用户id值不正确");
            return this;
        }
        return this;
    }

    public SpUserShopApiValidation shopId() {
        if (null == request.getShopId()) {
            validationMessageList.add("商家id不能为空");
            return this;
        }

        if (request.getShopId() < 1 || request.getShopId() > Long.MAX_VALUE) {
            validationMessageList.add("商家id值不正确");
            return this;
        }
        return this;
    }

    public boolean hasErrors() {

        return !validationMessageList.isEmpty();
    }

    public List<String> getErrors() {

        return validationMessageList;
    }

}
