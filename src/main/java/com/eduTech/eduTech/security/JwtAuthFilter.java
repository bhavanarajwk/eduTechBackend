package com.eduTech.eduTech.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {

            String authHeader = request.getHeader("Authorization");
            System.out.println("AUTH HEADER → " + authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("NO TOKEN FOUND");
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);
            System.out.println("TOKEN RECEIVED → " + token);

            String username = jwtService.extractUsername(token);
            System.out.println("USERNAME FROM TOKEN → " + username);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                Claims claims = jwtService.extractAllClaims(token);
                String role = claims.get("role", String.class);

                System.out.println("ROLE FROM TOKEN → " + role);

                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("AUTH SUCCESSFULLY SET");
            }

        } catch (Exception e) {
            System.out.println("JWT FILTER ERROR → " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}
