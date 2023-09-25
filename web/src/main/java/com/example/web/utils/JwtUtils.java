package com.example.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.example.db.domain.User;
import com.example.db.vo.user.UserVO;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    /** token秘钥*/
    public static final String JWT_SECRET = "chuu";
    /** token 过期时间: 10天 */
    public static final int CALENDARFIELD = Calendar.DATE;
    public static final int CALENDARINTERVAL = 10;
    public static  String USER_ID;
    public static  String USER_NAME;
    public static  String USER_AVATAR;
    public static  String USER_ROLE;

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
                .withClaim(USER_AVATAR, user.getAvatar())
                .withClaim(USER_ROLE, user.getRole())
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(JWT_SECRET)); // signature
        return token;
    }



    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static UserVO verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
            Map<String, Claim> claims = verifier.verify(token).getClaims();
            UserVO userVO = new UserVO(claims.get(USER_ID).asInt(), claims.get(USER_NAME).asString(), claims.get(USER_ROLE).asInt(), claims.get(USER_ID).asString());
            return userVO;
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return null;
    }

}

