package com.zl.way.mp.api;

import com.zl.way.mp.model.UserLoginBo;
import com.zl.way.mp.model.UserLoginParam;
import com.zl.way.mp.model.UserLoginRequest;
import com.zl.way.mp.model.UserLoginResponse;
import com.zl.way.mp.service.UserService;
import com.zl.way.util.PageParam;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("mpUserApi")
@RequestMapping("/mp/user")
public class UserApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @PostMapping("list")
    public ResponseResult<UserLoginResponse> queryUserList(@RequestBody UserLoginRequest request,
            @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setId(request.getId());
        userLoginParam.setLoginTel(request.getLoginTel());
        userLoginParam.setLoginName(request.getLoginName());

        PageParam pageParam = new PageParam();
        pageParam.setPageNum(request.getPageNum());
        pageParam.setPageSize(request.getPageSize());

        List<UserLoginBo> userLoginBoList = userService
                .queryUserLoginList(userLoginParam, pageParam);

        UserLoginResponse response = new UserLoginResponse();
        response.setUserLoginBoList(userLoginBoList);
        response.setUserLoginTotal(userService.queryUserLoginCount(userLoginParam));
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("disable")
    public ResponseResult<UserLoginResponse> disableUserLogin(@RequestBody UserLoginRequest request,
            @RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setId(request.getId());

        userService.disableUserLogin(userLoginParam);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}