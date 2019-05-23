package com.zl.way.mp.api.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.zl.way.mp.api.model.WayCommodityAbstractWordRelationshipRequest;

public class WayCommodityAbstractWordRelationshipApiValidation {

    private WayCommodityAbstractWordRelationshipRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public WayCommodityAbstractWordRelationshipApiValidation(WayCommodityAbstractWordRelationshipRequest request) {

        this.request = request;
    }

    public WayCommodityAbstractWordRelationshipApiValidation id() {

        if (null == request.getId()) {
            validationMessageList.add("id不能为空");
            return this;
        }

        if (request.getId() < 1 || request.getId() > Integer.MAX_VALUE) {
            validationMessageList.add("id值不正确");
            return this;
        }
        return this;
    }

    public WayCommodityAbstractWordRelationshipApiValidation abstractWordId() {

        if (null == request.getAbstractWordId()) {
            validationMessageList.add("abstractWordId不能为空");
            return this;
        }

        if (request.getAbstractWordId() < 1 || request.getAbstractWordId() > Integer.MAX_VALUE) {
            validationMessageList.add("abstractWordId值不正确");
            return this;
        }
        return this;
    }

    public WayCommodityAbstractWordRelationshipApiValidation abstractWordIdList() {

        if (CollectionUtils.isEmpty(request.getAbstractWordIdList())) {
            validationMessageList.add("abstractWordIdList不能为空");
            return this;
        }

        for (Integer n : request.getAbstractWordIdList()) {
            if (n < 1 || n > Integer.MAX_VALUE) {
                validationMessageList.add("abstractWordIdList值(" + n + ")不正确");
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

}
