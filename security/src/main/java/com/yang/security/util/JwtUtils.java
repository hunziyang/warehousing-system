package com.yang.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    // 过期时间5分钟
    public static final long EXPIRE_TIME = 30 * 60 * 1000;

    public static final long REFRESH_TIME = 5 * 30 * 1000;

    // 私钥
    public static String SECRET = "SECRET_YANG";

    // 请求头
    public static final String AUTH_HEADER = "Authorization";

    public static final String REFRESH_TOKEN = "Refresh_Token";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static String sign(String account) {
        Date nowDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+8")));
        //设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "hs256");
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(SECRET);
        return JWT
                .create()
                .withSubject(account)
                .withIssuedAt(nowDate)
                .sign(algorithm);
    }

    /**
     * 刷新 token的过期时间
     */
    public static String refreshToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return sign(jwt.getSubject());
    }
}
