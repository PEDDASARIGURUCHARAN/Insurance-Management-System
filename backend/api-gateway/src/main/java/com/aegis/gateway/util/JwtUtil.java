package com.aegis.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${aegis.jwt.secret}")
    private String jwtSecret;

    private Key getSigningKey() {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(jwtSecret);
        } catch (Exception e) {
            logger.warn("Configured jwtSecret is not valid Base64; falling back to UTF-8 bytes: {}", e.getMessage());
            keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractClaimsIfValid(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            logger.warn("Gateway JWT validation error: Token expired at {} - {}", e.getClaims().getExpiration(), e.getMessage());
        } catch (io.jsonwebtoken.security.SecurityException e) {
            logger.warn("Gateway JWT validation error: Invalid token signature (secret mismatch or tampered) - {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Gateway JWT validation error: Malformed JWT token - {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("Gateway JWT validation error: Unsupported JWT token - {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Gateway JWT validation error: JWT claims string is empty or invalid - {}", e.getMessage());
        } catch (Exception e) {
            logger.warn("Gateway JWT validation error: {}", e.getMessage());
        }
        return null;
    }

    public boolean validateToken(String token) {
        return extractClaimsIfValid(token) != null;
    }

    public Claims getAllClaimsFromToken(String token) {
        return extractClaimsIfValid(token);
    }

    public String getLoginId(String token) {
        Claims claims = extractClaimsIfValid(token);
        if (claims == null) return null;
        return claims.getSubject() != null ? claims.getSubject() : (String) claims.get("username");
    }

    public String getRole(String token) {
        Claims claims = extractClaimsIfValid(token);
        if (claims == null) return "";
        Object role = claims.get("role");
        return role != null ? role.toString() : "";
    }
}
