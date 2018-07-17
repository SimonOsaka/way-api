package com.zl.way.user.service;

import com.zl.way.user.model.UserLoginParam;
import com.zl.way.user.model.UserProfileBo;

public interface UserService {

    boolean userTelLogin(UserLoginParam userLoginParam);

    boolean userNameLogin(UserLoginParam userLoginParam);

    boolean userLogout(UserLoginParam userlogoutParam);

    UserProfileBo getUserById(Long userLoginId);

    UserProfileBo getUserByTel(String userLoginTel);

    UserProfileBo getUserByName(String userLoginName);

    String getUserValidCode(String userTel);
}
