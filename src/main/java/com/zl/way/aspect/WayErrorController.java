package com.zl.way.aspect;

import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class WayErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public ResponseResult<Object> error(HttpServletRequest request, HttpServletResponse response) {

        return ResponseResultUtil.wrapResponseResult(550, "访问错误", null);
    }

    @Override

    public String getErrorPath() {

        return ERROR_PATH;
    }
}
