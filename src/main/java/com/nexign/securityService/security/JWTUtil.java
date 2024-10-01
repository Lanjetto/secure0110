package com.nexign.securityService.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String username){
        Date issuedAt= new Date();
        Instant expiresAt = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant()).toInstant();


        return JWT.create()
                .withSubject("UserDetails")
                .withClaim("username", username)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .withIssuer("Nexign")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateToken(String token){
        return JWT.require(Algorithm.HMAC256(secret))
                .withSubject("UserDetails")
                .withIssuer("Nexign")
                .build()
                .verify(token)
                .getClaim("username").asString();
    }
}
