package com.zl.way.mp.api.validation;

import java.util.ArrayList;
import java.util.List;

import com.zl.way.mp.model.WayShopExtraRequest;

public class WayShopExtraApiValidation {

    private WayShopExtraRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public WayShopExtraApiValidation(WayShopExtraRequest request) {

        this.request = request;
    }

    public WayShopExtraApiValidation shopExtraId() {

        if (null == request.getId()) {
            validationMessageList.add("商家配置id不能为空");
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
