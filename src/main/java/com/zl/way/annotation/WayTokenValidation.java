package com.zl.way.annotation;

import java.lang.annotation.*;

/**
 * @author xuzhongliang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WayTokenValidation {

    /**
     * 指定哪个项目在使用，默认：mp
     * 
     * @return
     */
    String project() default "mp";

    /**
     * 指定request header中的token变量名称，默认：X-Token
     * 
     * @return
     */
    String headerToken() default "X-Token";

    /**
     * 指定request header中userLoginId变量名称，默认：X-userLoginId
     * 
     * @return
     */
    String headerUserLoginId() default "X-userLoginId";
}
