package com.auth.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final String SECRET_KET = "95ae09b4ff3417129aaad48f403542afa294df2393df1020e6c7246d3c58b6c7";

    public String generateAccessToken(UserDetails userDetails) {
        String token = generateToken(new HashMap<>(), userDetails);
        return token;
    }

    // generate refresh token without any expiration
    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .signWith(getKey(), SignatureAlgorithm.HS256)
        .compact();
        return token;
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public Claims extractALlClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims;
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims allClaims = extractALlClaims(token);
        return claimsResolver.apply(allClaims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        Date exp = null;
        exp = extractClaim(token, Claims::getExpiration);
        return exp;
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KET);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }
}
