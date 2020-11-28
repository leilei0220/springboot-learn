package com.leilei.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leilei.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 17:43
 * @desc
 */
@Service
public class JWTSupport {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expireTime}")
    private Long expireTime;

    /**
     * 生成token
     *
     * @param payLoadMap 负载
     * @return 返回token
     */
    public String buildToken(Map<String, String> payLoadMap) {
        JWTCreator.Builder jwt = JWT.create();
        payLoadMap.forEach(jwt::withClaim);
        jwt.withExpiresAt(new Date(System.currentTimeMillis() + expireTime * 60 * 60 * 1000));
        return jwt.sign(Algorithm.HMAC256(secret));
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public void verify(String token) {
        JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }

    /**
     * 获取token中payload
     *
     * @param token
     * @return
     */
    public User getUserInfoFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
        User user = new User();
        decodedJWT
                .getClaims()
                .forEach((k, v) -> {
                    if ("account".equals(k)) {
                        user.setAccount(v.asString());
                    }
                    if ("id".equals(k)) {
                        user.setId(Integer.valueOf(v.asString()));
                    }
                });
        return user;
    }
}
