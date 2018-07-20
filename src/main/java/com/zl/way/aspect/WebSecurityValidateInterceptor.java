package com.zl.way.aspect;

import com.zl.way.util.EnvUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebSecurityValidateInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(WebSecurityValidateInterceptor.class);

    private static final String HEADER_REFERER = "referer";

    @Value("${custom.security.host:''}")
    private String host;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {

        String referer = request.getHeader(HEADER_REFERER);
        String env = EnvUtil.getAppEnv();
        if (logger.isDebugEnabled()) {
            logger.debug("请求api之前referer={}，env={}，host={}", referer, env, host);
        }

        if (StringUtils.equalsIgnoreCase(env, EnvUtil.ENV_TEST) || StringUtils
                .equalsIgnoreCase(env, EnvUtil.ENV_PRODUCTION)) {
            if (StringUtils.indexOf(referer, host) == -1) {
                logger.warn("安全校验不通过，环境={}，referer={}，host={}", env, referer, host);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {

    }

}
