package com.example.common.constant;

import java.util.Calendar;

public class JwtConstant {
    /** token秘钥*/
    public static final String JWT_SECRET = "nixx";
    /** token 过期时间: 10天 */
    public static final int CALENDARFIELD = Calendar.DATE;
    public static final int CALENDARINTERVAL = 10;
}
