package com.feidao.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.support.FormatterPropertyEditorAdapter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class MyJwt {

    private static final String SECRET_STRING = "aXbK9vL2mNpQ7rT4wYzE1cF6gH3jS8uD";
    public static String generateToken(Map<String, Object> map) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
        return Jwts.builder()
                .signWith(key)
                .claims(map)
                .expiration(new Date(System.currentTimeMillis() + 86400000))
                .compact();
    }

    public static Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
