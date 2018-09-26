package com.zl.way.mp.api;

import com.zl.way.mp.model.LoginRequest;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController("loginApi")
@RequestMapping("/mp/login")
public class LoginApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Map<String, Object> RESULT_ADMIN = new HashMap<>();

    private static final Map<String, Object> RESULT_EDITOR = new HashMap<>();

    static {
        //admin
        RESULT_ADMIN.put("roles", Arrays.asList("admin"));
        RESULT_ADMIN.put("userLoginId", 8);
        RESULT_ADMIN.put("token", "40ea1605f376771837e8e9787687e8f9");
        RESULT_ADMIN.put("introduction", "我是超级管理员");
        RESULT_ADMIN.put("avatar",
                "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        RESULT_ADMIN.put("name", "Super Admin");
        //editor
        RESULT_EDITOR.put("roles", Arrays.asList("editor"));
        RESULT_EDITOR.put("userLoginId", 9);
        RESULT_EDITOR.put("token", "a43138e6f02c61e613e13fbc715fcb53");
        RESULT_EDITOR.put("introduction", "我是编辑");
        RESULT_EDITOR.put("avatar",
                "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        RESULT_EDITOR.put("name", "Normal Editor");
    }

    @PostMapping("login")
    public ResponseResult<Map<String, Object>> loginByUserName(@RequestBody LoginRequest request) {

        if (StringUtils.isBlank(request.getUsername())) {
            return ResponseResultUtil.wrapWrongParamResponseResult("用户名必填");
        }

        if (StringUtils.equalsIgnoreCase(request.getUsername(), "admin")) {
            return ResponseResultUtil.wrapSuccessResponseResult(RESULT_ADMIN);
        } else if (StringUtils.equalsIgnoreCase(request.getUsername(), "editor")) {
            return ResponseResultUtil.wrapSuccessResponseResult(RESULT_EDITOR);
        } else {
            return ResponseResultUtil.wrapWrongParamResponseResult("用户名不存在");
        }

    }

    @PostMapping("logout")
    public ResponseResult<String> logout(@RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }
        return ResponseResultUtil.wrapSuccessResponseResult("success");

    }

    @PostMapping("/user/info")
    public ResponseResult<Map<String, Object>> userInfo(@RequestHeader("X-Token") String userToken,
            @RequestHeader("X-userLoginId") Long userLoginId) {

        if (!TokenUtil.validToken(String.valueOf(userLoginId), userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            return ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
        }

        if (StringUtils.equalsIgnoreCase(userToken, "40ea1605f376771837e8e9787687e8f9")) {
            return ResponseResultUtil.wrapSuccessResponseResult(RESULT_ADMIN);
        } else if (StringUtils.equalsIgnoreCase(userToken, "a43138e6f02c61e613e13fbc715fcb53")) {
            return ResponseResultUtil.wrapSuccessResponseResult(RESULT_EDITOR);
        } else {
            return ResponseResultUtil.wrapWrongParamResponseResult("用户不存在");
        }

    }

}