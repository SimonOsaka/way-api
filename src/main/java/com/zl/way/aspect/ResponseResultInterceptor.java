package com.zl.way.aspect;

import com.alibaba.fastjson.JSON;
import com.zl.way.util.ResponseResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ResponseResultInterceptor {

    private final Logger logger = LoggerFactory.getLogger(ResponseResultInterceptor.class);

    @Around("execution(* com.zl.way.*.api.*.*(..))")
    public Object aroundRequestAndResponse(ProceedingJoinPoint proceedingJoinPoint) {

        try {
            long start = System.currentTimeMillis();
            logger.info("api开始，方法：{}，参数：{}", getMethodName(proceedingJoinPoint),
                getArgsJsonString(proceedingJoinPoint));

            Object proceed = proceedingJoinPoint.proceed();

            long end = System.currentTimeMillis();
            logger.info("api返回{}，耗时{}ms", getReturnJsonString(proceed), end - start);

            return proceed;
        } catch (Throwable throwable) {// 捕捉未知异常
            logger.error("系统异常", throwable);
            return ResponseResultUtil.wrapResponseResult(500, "业务繁忙，稍后再试", null);
        }
    }

    private String getArgsJsonString(ProceedingJoinPoint proceedingJoinPoint) {

        try {
            return JSON.toJSONString(proceedingJoinPoint.getArgs());
        } catch (Exception e) {
            logger.error("获取调用方法参数异常", e);
            return StringUtils.EMPTY;
        }
    }

    private String getMethodName(ProceedingJoinPoint proceedingJoinPoint) {

        try {
            String target = proceedingJoinPoint.getTarget().getClass().getName();
            target += "." + proceedingJoinPoint.getSignature().getName();
            return target;
        } catch (Exception e) {
            logger.error("获取调用方法异常", e);
            return StringUtils.EMPTY;
        }
    }

    private String getReturnJsonString(Object returnValue) {

        try {
            return JSON.toJSONString(returnValue);
        } catch (Exception e) {
            logger.error("获取返回值异常", e);
            return StringUtils.EMPTY;
        }
    }
}
