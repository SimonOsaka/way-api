package com.zl.way.mp.api.validation;

import com.zl.way.mp.model.WayShopQualificationRequest;

import java.util.ArrayList;
import java.util.List;

public class WayShopQualificationApiValidation {

    private WayShopQualificationRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public WayShopQualificationApiValidation(WayShopQualificationRequest request) {

        this.request = request;
    }

    public WayShopQualificationApiValidation id() {

        if (null == request.getId()) {
            validationMessageList.add("资质id不能为空");
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
