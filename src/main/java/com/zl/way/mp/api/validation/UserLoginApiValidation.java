package com.zl.way.mp.api.validation;

import com.zl.way.mp.model.UserLoginRequest;

import java.util.ArrayList;
import java.util.List;

public class UserLoginApiValidation {

    private UserLoginRequest request;

    private List<String> validationMessageList = new ArrayList<>();

    public UserLoginApiValidation(UserLoginRequest request) {

        this.request = request;
    }

    public UserLoginApiValidation Id() {

        if (null == request.getId()) {
            validationMessageList.add("用户登录id不能为空");
            return this;
        }

        if (request.getId() < 1 || request.getId() > Long.MAX_VALUE) {
            validationMessageList.add("用户登录id值不正确");
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
