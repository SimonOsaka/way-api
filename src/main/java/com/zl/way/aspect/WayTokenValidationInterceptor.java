package com.zl.way.aspect;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;
import com.zl.way.annotation.WayTokenValidation;
import com.zl.way.mp.exception.BusinessException;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.TokenUtil;

/**
 * token验证拦截器
 * 
 * @author xuzhongliang
 */
public class WayTokenValidationInterceptor implements HandlerInterceptor {
    private static final String PROJECT_MP = "mp";
    private static final String PROJECT_SP = "sp";
    private static final String PROJECT_APP = "app";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        WayTokenValidation tokenValidation = method.getAnnotation(WayTokenValidation.class);
        // 不需要token验证
        if (null == tokenValidation) {
            logger.info("token不需要验证，方法：{}", method.getClass().getName() + "." + method.getName());
            return true;
        }

        String project = tokenValidation.project();

        Context context = new Context();
        switch (project) {
            case PROJECT_MP:
                context.setStrategy(new MpTokenValidationStrategy());
                break;
            case PROJECT_SP:
                context.setStrategy(new SpTokenValidationStrategy());
                break;
            case PROJECT_APP:
                context.setStrategy(new AppTokenValidationStrategy());
                break;
            default:
                logger.warn("project名称不正确，名称：{}", tokenValidation.project());
                throw new BusinessException("token验证-工程名称不正确");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("project名称验证通过");
        }

        return context.exec(request, response, tokenValidation);
    }

}

interface Strategy {
    boolean exec(HttpServletRequest request, HttpServletResponse response, WayTokenValidation tokenValidation)
        throws IOException;
}

class MpTokenValidationStrategy implements Strategy {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean exec(HttpServletRequest request, HttpServletResponse response, WayTokenValidation tokenValidation)
        throws IOException {
        String userToken = request.getHeader(tokenValidation.headerToken());
        String userLoginId = request.getHeader(tokenValidation.headerUserLoginId());

        if (!TokenUtil.validToken(userLoginId, userToken)) {
            logger.warn("Token安全校验不过，userId={}，userToken={}", userLoginId, userToken);
            ResponseResult responseResult = ResponseResultUtil.wrapWrongParamResponseResult("安全校验没有通过");
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().print(JSON.toJSONString(responseResult));
            response.getWriter().flush();
            return false;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("token, userLoginId验证通过");
        }

        return true;
    }
}

class SpTokenValidationStrategy implements Strategy {

    @Override
    public boolean exec(HttpServletRequest request, HttpServletResponse response, WayTokenValidation tokenValidation)
        throws IOException {
        return true;
    }
}

class AppTokenValidationStrategy implements Strategy {

    @Override
    public boolean exec(HttpServletRequest request, HttpServletResponse response, WayTokenValidation tokenValidation)
        throws IOException {
        return true;
    }
}

class Context {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public boolean exec(HttpServletRequest request, HttpServletResponse response, WayTokenValidation tokenValidation)
        throws IOException {
        return this.strategy.exec(request, response, tokenValidation);
    }
}