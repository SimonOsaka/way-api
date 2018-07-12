package com.zl.way.filter;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.zl.way.util.XssUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssValueFilter implements ValueFilter {

    private final Logger logger = LoggerFactory.getLogger(XssValueFilter.class);

    @Override
    public Object process(Object object, String name, Object value) {

        if (value != null && "java.lang.String".equals(value.getClass().getTypeName())) {
            if (logger.isDebugEnabled()) {
                //            logger.debug("object={},name={},value={}", object, name, value);
                logger.debug("value type={}", value.getClass().getTypeName());
            }

            value = XssUtil.cleanXSS(String.valueOf(value));
        }
        return value;
    }
}
