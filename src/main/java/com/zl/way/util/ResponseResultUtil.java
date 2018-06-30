package com.zl.way.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseResultUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResponseResultUtil.class);

    public static final <T> ResponseResult<T> wrapSuccessResponseResult(T data) {
        return wrapResponseResult(200, null, data);
    }

    public static final <T> ResponseResult<T> wrapWrongParamResponseResult(String msg) {
        return wrapResponseResult(800, msg, null);
    }

    public static final <T> ResponseResult<T> wrapNotExistResponseResult(String msg) {
        return wrapResponseResult(900, msg, null);
    }

    public static final <T> ResponseResult<T> wrapResponseResult(int code, String msg, T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        responseResult.setData(data);
        if (logger.isDebugEnabled()) {
            logger.debug("返回结果{}", responseResult);
        }
        return responseResult;
    }
}
