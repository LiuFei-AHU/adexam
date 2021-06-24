package com.ahu.ad.common.configuration;

import com.ahu.ad.common.interceptor.AccessInterceptor;
import com.ahu.ad.common.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebAppConfig implements WebMvcConfigurer
{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login","/login.html","/register","/register.html","/logout","/static/**","/error"
                );

        registry.addInterceptor(new AccessInterceptor())
                .excludePathPatterns("/static/**","/error");
    }
}
