package com.eduTech.eduTech.security;

import com.eduTech.eduTech.entity.User;
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

    public String generateToken(User user){

        return Jwts.builder()
                .setSubject(user.getEmail())
                // ✅ ENUM SAFE CONVERSION
                .claim("role", user.getRole().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(key)
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extract role from token
    public String extractRole(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }
}
