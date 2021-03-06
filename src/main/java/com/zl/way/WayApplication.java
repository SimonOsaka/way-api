package com.zl.way;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zl.way.util.EnvUtil;

@SpringBootApplication
@MapperScan(basePackages = {"com.zl.way.**.mapper"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableScheduling
public class WayApplication {

    private final Logger logger = LoggerFactory.getLogger(WayApplication.class);

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(WayApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        String env = EnvUtil.getAppEnv();
        if (StringUtils.equalsIgnoreCase(env, EnvUtil.ENV_DEVELOPMENT)) {
            logger.info("fastjson启用PrettyFormat，方便前端调试");
            fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        }
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // fastJsonConfig.setSerializeFilters(new XssValueFilter()); //NOSONAR
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    private CorsConfiguration buildConfig() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

}
