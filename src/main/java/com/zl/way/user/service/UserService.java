package com.zl.way.user.service;

import com.zl.way.user.model.UserLoginParam;
import com.zl.way.user.model.UserProfileBo;

public interface UserService {

    boolean userLogin(UserLoginParam userLoginParam);

    boolean userLogout(UserLoginParam userlogoutParam);

    UserProfileBo getUser(Long userLoginId);

    UserProfileBo getUser(String userTel);

    String getUserValidCode(String userTel);
}
