package com.example.web.utils;

import com.example.db.vo.user.UserVO;
import com.example.redis.RedisService;

import javax.annotation.Resource;

import static com.example.common.constant.RedisConstant.LOGIN_USER_KEY;

public class UserUtils {
    @Resource
    private static RedisService redisService;

    public static UserVO getUser(String token) {
        return  (UserVO)redisService.getCacheObject(LOGIN_USER_KEY + token);
    }
}
