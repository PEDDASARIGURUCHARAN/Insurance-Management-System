package com.aegis.gateway.filter;

import com.aegis.gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final JwtUtil jwtUtil;

    public AuthenticationFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            String method = request.getMethod() != null ? request.getMethod().name() : "";

            logger.info("Gateway AuthenticationFilter intercepting: {} {}", method, path);

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                logger.warn("Authentication failed for [{} {}]: Missing Authorization header", method, path);
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Missing Authorization header");
            }

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.regionMatches(true, 0, "Bearer ", 0, 7)) {
                logger.warn("Authentication failed for [{} {}]: Header does not start with Bearer", method, path);
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Invalid Authorization header format - Bearer prefix required");
            }

            String token = authHeader.substring(7).trim();
            if (token.isEmpty()) {
                logger.warn("Authentication failed for [{} {}]: Bearer token is empty", method, path);
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Empty Bearer token");
            }

            // Safe masked token for logging (e.g. eyJhbGciOi...dkHZjg)
            String maskedToken = token.length() > 16
                    ? token.substring(0, 10) + "..." + token.substring(token.length() - 6)
                    : "***";
            logger.info("Token received for [{} {}]: {}", method, path, maskedToken);

            Claims claims = jwtUtil.extractClaimsIfValid(token);
            if (claims == null) {
                logger.warn("Authentication failed for [{} {}]: Token signature invalid or token expired", method, path);
                return onError(exchange, HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token");
            }

            String username = claims.getSubject() != null ? claims.getSubject() : (String) claims.get("username");
            Object roleObj = claims.get("role");
            String role = roleObj != null ? roleObj.toString() : "";

            logger.info("Token signature & expiration valid. Extracted user='{}', role='{}', expiresAt='{}'",
                    username, role, claims.getExpiration());

            // Authorization decision
            logger.info("Authorization decision: ALLOWED for user='{}', role='{}' to {} {}", username, role, method, path);

            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-Auth-User-Login", username != null ? username : "")
                    .header("X-Auth-User-Role", role)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String jsonError = String.format("{\"status\":%d,\"error\":\"%s\",\"message\":\"%s\"}",
                httpStatus.value(), httpStatus.getReasonPhrase(), message);
        byte[] bytes = jsonError.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {
    }
}
