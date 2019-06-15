package com.zl.way.user.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.zl.way.user.api.model.*;
import com.zl.way.user.model.*;
import com.zl.way.user.service.UserService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final Logger logger = LoggerFactory.getLogger(UserApi.class);

    @Autowired
    private UserService userService;

    private static final Map<Byte, String> FEEDBACK_TYPE_MAP = new HashMap<>(5);

    @Value("${custom.user.agreementsUrl}")
    private String agreementsUrl;

    static {
        FEEDBACK_TYPE_MAP.put((byte)1, "商家反馈");
        FEEDBACK_TYPE_MAP.put((byte)2, "商品反馈");
        FEEDBACK_TYPE_MAP.put((byte)3, "优惠反馈");
        FEEDBACK_TYPE_MAP.put((byte)4, "账号反馈");
        FEEDBACK_TYPE_MAP.put((byte)99, "投诉建议");

    }

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

    @PostMapping("/device/sync")
    public ResponseResult<Void> syncUserDevice(@RequestBody UserDeviceRequest request) {

        if (null == request.getUserLoginId()) {
            return ResponseResultUtil.wrapWrongParamResponseResult(null);
        }

        UserDeviceParam param = BeanMapper.map(request, UserDeviceParam.class);
        userService.saveOrUpdateUserDevice(param);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/profile/address/update")
    public ResponseResult<Void> updateProfileAddress(@RequestBody UserProfileRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserProfileParam param = BeanMapper.map(request, UserProfileParam.class);

        userService.updateProfileAddress(param);
        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/address/list")
    public ResponseResult<UserAddressResponse> queryUserAddressList(@RequestBody UserAddressRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserAddressParam param = new UserAddressParam();
        param.setUserLoginId(request.getUserLoginId());
        List<UserAddressBo> userAddressBoList = userService.queryUserAddressList(param);

        UserAddressResponse response = new UserAddressResponse();
        response.setUserAddressBoList(userAddressBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/address/create")
    public ResponseResult<Void> createUserAddress(@RequestBody UserAddressRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserAddressParam param = new UserAddressParam();
        param.setName(request.getAddressName());
        param.setTag(request.getAddressTag());
        param.setLongitude(request.getAddressLongitude());
        param.setLatitude(request.getAddressLatitude());
        param.setUserLoginId(request.getUserLoginId());
        userService.saveUserAddress(param);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/address/update")
    public ResponseResult<Void> updateUserAddress(@RequestBody UserAddressRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserAddressParam param = new UserAddressParam();
        param.setName(request.getAddressName());
        param.setTag(request.getAddressTag());
        param.setLongitude(request.getAddressLongitude());
        param.setLatitude(request.getAddressLatitude());
        param.setId(request.getId());
        userService.updateUserAddress(param);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/address/delete")
    public ResponseResult<Void> deleteUserAddress(@RequestBody UserAddressRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserAddressParam param = new UserAddressParam();
        param.setId(request.getId());
        userService.deleteUserAddress(param);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }

    @PostMapping("/feedback/typeMap")
    public ResponseResult<UserFeedbackResponse> feedbackTypeMap(@RequestBody UserFeedbackRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserFeedbackResponse response = new UserFeedbackResponse();
        response.setFeedbackTypeMap(FEEDBACK_TYPE_MAP);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/feedback/list")
    public ResponseResult<UserFeedbackResponse> queryUserFeedbackList(@RequestBody UserFeedbackRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserFeedbackParam param = BeanMapper.map(request, UserFeedbackParam.class);
        List<UserFeedbackBo> userAddressBoList = userService.queryUserFeedbackList(param, request);

        UserFeedbackResponse response = new UserFeedbackResponse();
        response.setUserFeedbackBoList(userAddressBoList);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/feedback/get")
    public ResponseResult<UserFeedbackResponse> getUserFeedback(@RequestBody UserFeedbackRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserFeedbackParam param = new UserFeedbackParam();
        param.setId(request.getId());
        param.setUserLoginId(request.getUserLoginId());
        UserFeedbackBo userAddressBo = userService.getUserFeedback(param);

        UserFeedbackResponse response = new UserFeedbackResponse();
        response.setUserFeedbackBo(userAddressBo);
        return ResponseResultUtil.wrapSuccessResponseResult(response);
    }

    @PostMapping("/feedback/add")
    public ResponseResult<UserFeedbackResponse> addUserFeedback(@RequestBody UserFeedbackRequest request,
        @RequestHeader("token") String userToken) {

        if (!TokenUtil.validToken(String.valueOf(request.getUserLoginId()), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", request.getUserLoginId(), userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        UserFeedbackParam param = BeanMapper.map(request, UserFeedbackParam.class);
        userService.addUserFeedback(param);

        return ResponseResultUtil.wrapSuccessResponseResult(null);
    }
}