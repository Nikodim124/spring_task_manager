package com.taskmanager.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");



    }

    @Bean
    public DeviceResolverHandlerInterceptor
    deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    public DeviceHandlerMethodArgumentResolver
    deviceHandlerMethodArgumentResolver() {
        return new DeviceHandlerMethodArgumentResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(deviceResolverHandlerInterceptor());
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(deviceHandlerMethodArgumentResolver());
    }
}
