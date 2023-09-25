package com.example.common.constant;

public class RedisConstant {
    public static final String LOGIN_CODE_KEY = "login:code:";
    //验证码过期时间2分钟
    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:user:";
    //登录用户过期时间1天
    public static final Long LOGIN_USER_TTL = 1L;

}
