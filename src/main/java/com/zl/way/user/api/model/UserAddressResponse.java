package com.zl.way.user.api.model;

import com.zl.way.user.model.UserAddressBo;

import java.util.List;

public class UserAddressResponse {

    private List<UserAddressBo> userAddressBoList;

    public List<UserAddressBo> getUserAddressBoList() {
        return userAddressBoList;
    }

    public void setUserAddressBoList(List<UserAddressBo> userAddressBoList) {
        this.userAddressBoList = userAddressBoList;
    }
}
