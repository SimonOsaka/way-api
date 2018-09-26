package com.zl.way.mp.api.validation;

import com.zl.way.mp.model.WayDiscountRequest;

import java.util.ArrayList;
import java.util.List;

public class WayDiscountApiValidation {

    private WayDiscountRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public WayDiscountApiValidation(WayDiscountRequest request) {

        this.request = request;
    }

    public WayDiscountApiValidation discountId() {

        if (null == request.getDiscountId()) {
            validationMessageList.add("优惠id不能为空");
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
