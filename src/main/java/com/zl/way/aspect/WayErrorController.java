package com.zl.way.aspect;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
public class WayErrorController implements ErrorController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String ERROR_PATH = "/error";

    private final ErrorAttributes errorAttributes;

    public WayErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    /**
     * 错误返回，type=1参数校验失败，type=2参数有问题或其它问题。
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(ERROR_PATH)
    public Map<Object, Object> error(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Api-Generation", "2");
        response.setHeader("Access-Control-Expose-Headers", "Api-Generation");

        if (logger.isDebugEnabled()) {
            logger.debug("error message: {}",
                JSON.toJSONString(getErrorAttributes(request, false).get("errors"), true));
            logger.debug("error attributes: {}", JSON.toJSONString(getErrorAttributes(request, true), true));
        }

        Object errorsObject = getErrorAttributes(request, false).get("errors");
        if (null != errorsObject) {
            if (errorsObject instanceof List) {
                List<Object> errorsList = (List<Object>)errorsObject;
                Map<Object, Object> errorMap = new HashMap<>(errorsList.size());
                String errorListString = JSONArray.toJSONString(errorsList);
                JSONArray errorJsonArray = JSONArray.parseArray(errorListString);
                errorJsonArray.forEach(map -> {
                    JSONObject errorJsonObject = (JSONObject)map;
                    Object key = errorJsonObject.get("field");
                    Object value = errorJsonObject.get("defaultMessage");
                    errorMap.put(key, String.format("%s%s", key, value));
                });
                Map<Object, Object> resultMap = new HashMap<>(3);
                resultMap.put("type", 1);
                resultMap.put("msg", "参数校验错误");
                resultMap.put("parameters", errorMap);
                return resultMap;
            }
        } else {
            Map<String, Object> errorAttributesMap = getErrorAttributes(request, false);
            logger.warn("错误信息：{}", errorAttributesMap);
            Map<Object, Object> map = new HashMap<>(3);
            map.put("type", "2");
            map.put("msg", "访问错误");
            map.put("message", errorAttributesMap.getOrDefault("message", StringUtils.EMPTY));
            return map;
        }

        return Collections.EMPTY_MAP;
    }

    @Override
    public String getErrorPath() {

        return ERROR_PATH;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest requestAttributes = new ServletWebRequest(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}
