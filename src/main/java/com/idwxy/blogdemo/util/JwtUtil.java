package com.idwxy.blogdemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    // 过期时间 7 天，这里的时间单位为毫秒
    private final static long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * 生成签名，7 天后过期
     * @param userName 用户名
     * @param secret 用户的密码
     * @return 加密的 token
     */
    public static String sign(String userName, String secret) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withClaim("userName", userName)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     * @param token 密钥
     * @param userName 用户名
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userName, String secret) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userName", userName)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取 token 中的信息无需 secret 解密也能活得
     * @param token 密钥
     * @return token中包含的用户名
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
