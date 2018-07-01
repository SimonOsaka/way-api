package com.zl.way.aspect;

import com.zl.way.util.ResponseResultUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ResponseResultInterceptor {
    private Logger logger = LoggerFactory.getLogger(ResponseResultInterceptor.class);

    @Around("execution(* com.zl.way.*.api.*.*(..))")
    public Object aroundRequestAndResponse(ProceedingJoinPoint proceedingJoinPoint) {

        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {//捕捉未知异常
            logger.error("系统异常", throwable);
            return ResponseResultUtil.wrapResponseResult(500, "业务繁忙，稍后再试", null);
        }
    }
}
