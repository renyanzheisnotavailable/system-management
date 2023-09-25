package com.example.starter.handler;

import cn.hutool.core.util.StrUtil;
import com.example.common.utils.UserHolder;
import com.example.db.domain.User;
import com.example.redis.RedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Handler;

import static com.example.common.constant.RedisConstant.LOGIN_USER_KEY;
import static com.example.common.constant.RedisConstant.LOGIN_USER_TTL;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    private RedisService redisService;

    public RefreshTokenInterceptor(RedisService redisService){
        this.redisService = redisService;
    }



//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("authorization");
//        if(StrUtil.isBlank(token)) {
//            return false;
//        }
//        User user = redisService.getCacheObject(LOGIN_USER_KEY + token);
//        redisService.expire(LOGIN_USER_KEY + token, LOGIN_USER_TTL);
//        return true;
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");
        if(StrUtil.isBlank(token)) {
            return false;
        }
        redisService.expire(LOGIN_USER_KEY + token);
        return true;
    }




}
