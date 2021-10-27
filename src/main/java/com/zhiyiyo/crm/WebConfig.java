package com.zhiyiyo.crm;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zhiyiyo.crm.interceptor.UserInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] includes = {"/workbench/**"};
        String[] excludes = {};

        // 注册拦截器
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns(includes)
                .excludePathPatterns(excludes);
    }
}























