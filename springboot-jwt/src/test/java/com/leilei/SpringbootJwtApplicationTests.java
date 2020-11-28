package com.leilei;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

@SpringBootTest
class SpringbootJwtApplicationTests {

    @Test
    void contextLoads() {
        String jwt = JWT.create()
                .withClaim("username", "admin")
                .withExpiresAt(new Date(System.currentTimeMillis()+60000))
                .sign(Algorithm.HMAC256("secret"));
        System.out.println(jwt);

    }

    @Test
    void verifyJWT() {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("secret")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDY1NTYxNDEsInVzZXJuYW1lIjoiYWRtaW4ifQ.3ntG0riOBHS-5763ze0IkxlmNgjUJZCFtOH6JClkrGA");
        String payload = decodedJWT.getPayload();
        System.out.println("负载:" + payload);
        System.out.println("标头:" + decodedJWT.getHeader());
        System.out.println("签名:" + decodedJWT.getSignature());
        System.out.println("------");
        Map<String, Claim> payloads = decodedJWT.getClaims();
        payloads.forEach((k,v)-> System.out.println( k + ":" + v.asString()));
    }

}
