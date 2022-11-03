package com.leilei.jwt;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.leilei.entity.UserSub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author lei
 * @version 1.0
 * @date 2020/11/28 17:43
 * @desc
 */
@Service
public class JwtSupport {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expireTime}")
    private Long expireTime;

    /**
     * 生成token
     *
     * @param payload 载荷
     * @return 返回token
     */
    public String buildToken(UserSub payload) {
        JWTCreator.Builder jwt = JWT.create();
        // 设置payload
        jwt.withSubject(JSON.toJSONString(payload));
        // 设置过期时间
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
     * @param jwt
     * @return
     */
    public UserSub parseJwt(String jwt) {
        DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256(secret)).build().verify(jwt);
        String subject = decodedJwt.getSubject();
        return JSON.parseObject(subject, UserSub.class);
    }
}
