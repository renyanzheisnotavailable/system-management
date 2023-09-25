//package com.example.starter.config;
//
//import com.example.redis.RedisService;
//import com.example.starter.handler.RefreshTokenInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class MvcConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private RedisService redisService;
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new RefreshTokenInterceptor(redisService)).order(0);
//    }
//}
