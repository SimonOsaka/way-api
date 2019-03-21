package com.zl.way.user.service;

import com.zl.way.user.model.*;

import java.util.List;

public interface UserService {

    boolean userTelLogin(UserLoginParam userLoginParam);

    boolean userNameLogin(UserLoginParam userLoginParam);

    boolean userLogout(UserLoginParam userlogoutParam);

    UserProfileBo getUserById(Long userLoginId);

    UserProfileBo getUserByTel(String userLoginTel);

    UserProfileBo getUserByName(String userLoginName);

    String getUserValidCode(String userTel);

    boolean saveOrUpdateUserDevice(UserDeviceParam param);

    void updateProfileAddress(UserProfileParam param);

    List<UserAddressBo> queryUserAddressList(UserAddressParam param);

    void saveUserAddress(UserAddressParam param);

    void updateUserAddress(UserAddressParam param);

    void deleteUserAddress(UserAddressParam param);
}
