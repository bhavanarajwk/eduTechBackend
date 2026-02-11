package com.eduTech.eduTech.security;

import com.eduTech.eduTech.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Secret key (later move to env variable)
    private final String SECRET = "mysecretkeymysecretkeymysecretkey123";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Generate JWT Token
    public String generateToken(User user){

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", "ROLE_" + user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(key)
                .compact();
    }

    // Extract email (Subject)
    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    // Extract username (same as email)
    public String extractUsername(String token){
        return extractEmail(token);
    }

    // Extract role
    public String extractRole(String token){
        return extractAllClaims(token).get("role", String.class);
    }

    // Extract all claims
    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
