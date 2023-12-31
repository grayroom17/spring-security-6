package com.example.section10.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.section10.constants.SecurityConstants.JWT_HEADER;
import static com.example.section10.constants.SecurityConstants.JWT_KEY;

public class JwtGeneratorFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            SecretKey key = Keys.hmacShaKeyFor(JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .issuer("Eazy Bank")
                    .subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(key).compact();
            response.setHeader(JWT_HEADER, jwt);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/user");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return String.join(",", authoritiesSet);
    }

}
