package com.zl.way.user.api;

import com.zl.way.user.api.model.UserRequest;
import com.zl.way.user.api.model.UserResponse;
import com.zl.way.user.model.UserLoginParam;
import com.zl.way.user.model.UserProfile;
import com.zl.way.user.model.UserProfileBo;
import com.zl.way.user.service.UserService;
import com.zl.way.util.BeanMapper;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult<UserResponse> userLogin(@RequestBody UserRequest userRequest) {

        UserLoginParam userLoginParam = new UserLoginParam();
        userLoginParam.setLoginTel(userRequest.getUserTel());
        userLoginParam.setValidCode(userRequest.getValidCode());

        try {
            if (userService.userLogin(userLoginParam)) {
                UserProfileBo userProfileBo = userService.getUser(userRequest.getUserTel());
                UserProfile userProfile = BeanMapper.map(userProfileBo, UserProfile.class);
                UserResponse userResponse = BeanMapper.map(userProfile, UserResponse.class);
                return ResponseResultUtil.wrapSuccessResponseResult(userResponse);
            }
        } catch (RuntimeException re) {
            return ResponseResultUtil.wrapWrongParamResponseResult(re.getMessage());
        }

        return ResponseResultUtil.wrapWrongParamResponseResult("用户登录异常");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseResult<UserResponse> userLogout(@RequestBody UserRequest userRequest) {

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
}
