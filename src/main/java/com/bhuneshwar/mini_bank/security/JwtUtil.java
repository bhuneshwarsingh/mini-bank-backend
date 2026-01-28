package com.bhuneshwar.mini_bank.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getSigninKey()
    {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String email,String role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(getSigninKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    public String extractEmail(String token)
    {
        return parseClaims(token).getSubject();
    }
    public String extractRole(String token)
    {
        return parseClaims(token).get("role",String.class);
    }
    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired!");
            return false;
        } catch (JwtException e) {
            System.out.println("Token invalid!");
            return false;
        }
    }
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
