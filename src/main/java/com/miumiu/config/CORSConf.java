package com.miumiu.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author chimmhuang
 * @date 2019/1/8 0008 11:49
 * @description 全局CORS跨域配置
 */
@Configuration
public class CORSConf {
    private static final Logger LOG = LoggerFactory.getLogger(CorsConfiguration.class);

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (LOG.isInfoEnabled()) {
                    LOG.info("初始化 CORSConfiguration 配置");
                }
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        .allowCredentials(true);
            }
        };
    }
}
