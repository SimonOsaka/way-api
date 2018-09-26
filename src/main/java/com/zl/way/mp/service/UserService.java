package com.zl.way.mp.service;

import com.zl.way.mp.model.UserLoginBo;
import com.zl.way.mp.model.UserLoginParam;
import com.zl.way.util.PageParam;

import java.util.List;

public interface UserService {

    List<UserLoginBo> queryUserLoginList(UserLoginParam param, PageParam pageParam);

    Long queryUserLoginCount(UserLoginParam param);

    UserLoginBo disableUserLogin(UserLoginParam param);
}
