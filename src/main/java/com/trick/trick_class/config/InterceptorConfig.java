package com.trick.trick_class.config;

import com.trick.trick_class.interceptor.CorsInterceptor;
import com.trick.trick_class.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 *
 * 不用权限可以访问url /api/v1/pub/ 这种请求都是不用拦截的
 * 注意下面的有一个问题是访问log界面即使已经登录过了，也要重新登陆，这显然是需要改进的
 * 要登录可以访问url /api/v1/pri/
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");//拦截所有请求允许跨域

        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/api/v1/pri/*/*/**")
                .excludePathPatterns("/api/v1/pri/user/login","/api/v1/pri/user/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
