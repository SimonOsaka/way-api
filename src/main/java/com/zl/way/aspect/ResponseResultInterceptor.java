package com.zl.way.aspect;

import com.alibaba.fastjson.JSON;
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
	private final Logger logger = LoggerFactory.getLogger(ResponseResultInterceptor.class);

	@Around("execution(* com.zl.way.*.api.*.*(..))")
	public Object aroundRequestAndResponse(ProceedingJoinPoint proceedingJoinPoint) {

		try {
			long start = System.currentTimeMillis();
			logger.info("api开始");

			Object proceed = proceedingJoinPoint.proceed();

			long end = System.currentTimeMillis();
			logger.info("api返回{}，耗时{}ms", JSON.toJSONString(proceed), end - start);

			return proceed;
		} catch (Throwable throwable) {//捕捉未知异常
			logger.error("系统异常", throwable);
			return ResponseResultUtil.wrapResponseResult(500, "业务繁忙，稍后再试", null);
		}
	}
}
