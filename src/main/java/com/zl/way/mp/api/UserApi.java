package com.zl.way.mp.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.api.validation.UserLoginApiValidation;
import com.zl.way.mp.model.UserLoginBo;
import com.zl.way.mp.model.UserLoginParam;
import com.zl.way.mp.model.UserLoginRequest;
import com.zl.way.mp.model.UserLoginResponse;
import com.zl.way.mp.service.UserService;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;

@RestController("mpUserApi")
@RequestMapping("/mp/user")
public class UserApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @PostMapping("list")
    @WayTokenValidation
    public ResponseResult<UserLoginResponse> queryUserList(@RequestBody UserLoginRequest request) {

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setId(request.getId());
        userLoginParam.setLoginTel(request.getLoginTel());
        userLoginParam.setLoginName(request.getLoginName());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<UserLoginBo> userLoginBoList = userService.queryUserLoginList(userLoginParam, pageParam);

        UserLoginResponse response = new UserLoginResponse();
        response.setUserLoginBoList(userLoginBoList);
        response.setUserLoginTotal(userService.queryUserLoginCount(userLoginParam));
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("disable")
    @WayTokenValidation
    public ResponseResult<UserLoginResponse> disableUserLogin(@RequestBody UserLoginRequest request) {

        UserLoginApiValidation loginApiValidation = new UserLoginApiValidation(request).Id();
        if (loginApiValidation.hasErrors()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(loginApiValidation.getErrors().get(0));
        }

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setId(request.getId());

        userService.disableUserLogin(userLoginParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}