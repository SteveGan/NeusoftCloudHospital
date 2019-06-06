package com.neuedu.hospitalbackend.config;

import com.neuedu.hospitalbackend.component.JwtInterceptor;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 * @author Raven
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //添加拦截器//放掉某些特定不需要校验token的路由
        registry.addInterceptor(new JwtInterceptor()).excludePathPatterns("/oauth/login", "/oauth/register");
    }

}
