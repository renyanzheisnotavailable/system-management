package com.example.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.example.db.domain.User;
import com.example.api.vo.user.UserVO;
import com.example.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.common.constant.RedisConstant.LOGIN_USER_KEY;

@Component
public class JwtUtils {


    private static RedisService redisService ;

    @Autowired
    JwtUtils(RedisService redisService1) {
        redisService = redisService1;
    }

    /** token秘钥*/
    public static final String JWT_SECRET = "chuu";
    /** token 过期时间: 10天 */
    public static final int CALENDARFIELD = Calendar.DATE;
    public static final int CALENDARINTERVAL = 1;
    public static  String USER_ID = "user_id";
    public static  String USER_NAME = "user_name";
    public static  String USER_AVATAR = "user_avatar";
    public static  String USER_ROLE = "user_role";
    public static  String USER_DEPARTMENT_ID = "user_department_id";
    public static  String USER_COMPANY_ID = "user_company_id";

    /**
     * 根据用户创建token
     *
     * @param user
     * @return
     */
    public static String createToken(User user)  {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(CALENDARFIELD, CALENDARINTERVAL);
        Date expiresDate = nowTime.getTime();
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "chu") // payload
                .withClaim(USER_ID, user.getId())
                .withClaim(USER_NAME, user.getName())
                .withClaim(USER_ROLE, user.getRole())
                .withClaim(USER_AVATAR, user.getAvatar())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(JWT_SECRET)); // signature
        return token;
    }



    /**
     * 解密Token并刷新
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static UserVO verifyToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        Map<String, Claim> claims = verifier.verify(token).getClaims();
        UserVO userVO = new UserVO();
        userVO.setId(claims.get(USER_ID).asInt());
        userVO.setName(claims.get(USER_NAME).asString());
        userVO.setRole(claims.get(USER_ROLE).asInt());
        userVO.setAvatar(claims.get(USER_AVATAR).asString());

        //刷新redis中的更新时间
        redisService.expire(LOGIN_USER_KEY + token);
        return userVO;

    }


}

