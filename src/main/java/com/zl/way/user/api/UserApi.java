package com.zl.way.user.api;

import com.zl.way.user.api.model.UserDeviceRequest;
import com.zl.way.user.api.model.UserRequest;
import com.zl.way.user.api.model.UserResponse;
import com.zl.way.user.model.UserDeviceParam;
import com.zl.way.user.model.UserLoginParam;
import com.zl.way.user.model.UserProfile;
import com.zl.way.user.model.UserProfileBo;
import com.zl.way.user.service.UserService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/user") public class UserApi {

    private final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired private UserService userService;

    @Value("${custom.user.agreementsUrl}") private String agreementsUrl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult<UserResponse> userLogin(@RequestBody UserRequest userRequest) {

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setLoginTel(userRequest.getUserTel());
        userLoginParam.setValidCode(userRequest.getValidCode());

        try {
            if (userService.userTelLogin(userLoginParam)) {
                UserProfileBo userProfileBo = userService.getUserByTel(userRequest.getUserTel());
                UserProfile userProfile = BeanMapper.map(userProfileBo, UserProfile.class);
                UserResponse userResponse = BeanMapper.map(userProfile, UserResponse.class);
                userResponse.setToken(TokenUtil.getToken(String.valueOf(userProfileBo.getUserLoginId())));
                return ResponseResultUtil.wrapSuccessResponseResult(userResponse);
            }
        } catch (RuntimeException re) {
            return ResponseResultUtil.wrapWrongParamResponseResult(re.getMessage());
        }

        return ResponseResultUtil.wrapWrongParamResponseResult("用户登录异常");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseResult<UserResponse> userLogout(@RequestBody UserRequest userRequest,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(userRequest.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userRequest.getUserLoginId(), userToken);
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

    /*@RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseResult<UserResponse> getUserDetail(UserRequest userRequest) {

        try {
            UserProfileBo userProfileBo = userService.getUser(userRequest.getUserLoginId());
            UserResponse userResponse = BeanMapper.map(userProfileBo, UserResponse.class);
            return ResponseResultUtil.wrapSuccessResponseResult(userResponse);
        } catch (RuntimeException re) {
            return ResponseResultUtil.wrapWrongParamResponseResult(re.getMessage());
        }

    }*/

    @RequestMapping(value = "/validCode", method = RequestMethod.POST)
    public ResponseResult<UserResponse> getUserValidCode(@RequestBody UserRequest userRequest) {

        try {
            userService.getUserValidCode(userRequest.getUserTel());
            return ResponseResultUtil.wrapSuccessResponseResult(new UserResponse());
        } catch (RuntimeException re) {
            return ResponseResultUtil.wrapWrongParamResponseResult(re.getMessage());
        }
    }

    @RequestMapping(value = "/agreements", method = RequestMethod.POST)
    public ResponseResult<UserResponse> getAgreements() {

        UserResponse userResponse = new UserResponse();
        userResponse.setUserAgreementsUrl(agreementsUrl);
        return ResponseResultUtil.wrapSuccessResponseResult(userResponse);
    }

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseResult<UserResponse> userSignin(@RequestBody UserRequest userRequest) {

        if (StringUtils.isBlank(userRequest.getUserLoginName())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("手机号必须填写");
        }

        if (StringUtils.isBlank(userRequest.getUserLoginPassword())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("密码必须填写");
        }

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setLoginName(userRequest.getUserLoginName());
        userLoginParam.setLoginPassword(userRequest.getUserLoginPassword());

        try {
            if (userService.userNameLogin(userLoginParam)) {
                UserProfileBo userProfileBo = userService.getUserByName(userRequest.getUserLoginName());
                UserProfile userProfile = BeanMapper.map(userProfileBo, UserProfile.class);
                UserResponse userResponse = BeanMapper.map(userProfile, UserResponse.class);
                userResponse.setToken(TokenUtil.getToken(String.valueOf(userProfileBo.getUserLoginId())));
                return ResponseResultUtil.wrapSuccessResponseResult(userResponse);
            }
        } catch (Exception e) {
            return ResponseResultUtil.wrapWrongParamResponseResult(e.getMessage());
        }
        return ResponseResultUtil.wrapWrongParamResponseResult("用户登录异常");
    }

    @PostMapping("/device/sync") public ResponseResult<Void> syncUserDevice(@RequestBody UserDeviceRequest request) {

        if (null == request.getUserLoginId()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(null);
        }

        UserDeviceParam param = BeanMapper.map(request, UserDeviceParam.class);
        userService.saveOrUpdateUserDevice(param);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

}