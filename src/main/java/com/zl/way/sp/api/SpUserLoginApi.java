package com.zl.way.sp.api;

import com.zl.way.sp.model.*;
import com.zl.way.sp.service.SpUserShopService;
import com.zl.way.sp.service.WayShopService;
import com.zl.way.user.api.model.UserRequest;
import com.zl.way.user.api.model.UserResponse;
import com.zl.way.user.model.UserLoginParam;
import com.zl.way.user.model.UserProfileBo;
import com.zl.way.user.service.UserService;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("spUserLoginApi")
@RequestMapping("/sp/user")
public class SpUserLoginApi {

    private final Logger logger = LoggerFactory.getLogger(SpUserLoginApi.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SpUserShopService spUserShopService;

    @Autowired
    private WayShopService shopService;

    @PostMapping(value = "/login")
    public ResponseResult<SpUserResponse> login(@RequestBody SpUserRequest request) {

        if (StringUtils.isBlank(request.getUserLoginName())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("用户名必须填写");
        }

        if (StringUtils.isBlank(request.getUserLoginPassword())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("用户密码必须填写");
        }

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setLoginName(request.getUserLoginName());
        userLoginParam.setLoginPassword(request.getUserLoginPassword());

        try {
            if (userService.userNameLogin(userLoginParam)) {
                UserProfileBo userProfileBo = userService.getUserByName(request.getUserLoginName());
                SpUserResponse response = new SpUserResponse();
                response.setToken(
                        TokenUtil.getToken(String.valueOf(userProfileBo.getUserLoginId())));
                return ResponseResultUtil.wrapSuccessResponseResult(response);
            }
        } catch (Exception e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }
        return ResponseResultUtil.wrapWrongParamResponseResult("用户登录异常");
    }

    @PostMapping(value = "/info")
    public ResponseResult<SpUserResponse> info(@RequestBody SpUserRequest userRequest,
            @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(userRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userRequest.getUserLoginId(),
                    userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }
        try {
            SpUserResponse response = new SpUserResponse();

            UserProfileBo userProfileBo = userService.getUserById(userRequest.getUserLoginId());
            response.setProfile(userProfileBo);

            SpUserShopParam spUserShopParam = new SpUserShopParam();
            spUserShopParam.setUserLoginId(userRequest.getUserLoginId());
            SpUserShopBo spUserShopBo = spUserShopService.getUserShop(spUserShopParam);
            if (null != spUserShopBo) {
                WayShopParam shopParam = new WayShopParam();
                shopParam.setId(spUserShopBo.getShopId());
                WayShopBo wayShopBo = shopService.getShop(shopParam);
                response.setShop(wayShopBo);
            }
            return ResponseResultUtil.wrapSuccessResponseResult(response);
        } catch (RuntimeException re) {
            return ResponseResultUtil.wrapWrongParamResponseResult(re.getMessage());
        }

    }

    @PostMapping(value = "/logout")
    public ResponseResult<UserResponse> logout(@RequestBody UserRequest userRequest,
            @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(userRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userRequest.getUserLoginId(),
                    userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setId(userRequest.getUserLoginId());

        try {
            if (userService.userLogout(userLoginParam)) {
                return ResponseResultUtil.wrapSuccessResponseResult(null);
            }
        } catch (RuntimeException re) {
            return ResponseResultUtil.wrapWrongParamResponseResult(re.getMessage());
        }

        return ResponseResultUtil.wrapWrongParamResponseResult("退出登录异常");
    }

}
