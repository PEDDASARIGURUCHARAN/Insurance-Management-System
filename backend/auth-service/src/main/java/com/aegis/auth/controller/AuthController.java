package com.aegis.auth.controller;

import com.aegis.auth.dto.*;
import com.aegis.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/login
     * Authenticate with loginId (or email) and password, return JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/auth/register
     * Register a new user and receive a JWT token.
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/auth/validate
     * Validate a token provided in Authorization header or as query parameter.
     */
    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validateTokenGet(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestParam(value = "token", required = false) String tokenParam) {

        String token = authHeader != null ? authHeader : tokenParam;
        ValidateTokenResponse response = authService.validateToken(token);

        if (response.isValid()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * POST /api/auth/validate
     * Validate a token provided in Authorization header or request body.
     */
    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> validateTokenPost(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody(required = false) Map<String, String> body) {

        String token = authHeader;
        if (token == null && body != null) {
            token = body.get("token");
        }

        ValidateTokenResponse response = authService.validateToken(token);
        if (response.isValid()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * GET /api/auth/me
     * Get current user profile from valid JWT token.
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @RequestHeader("Authorization") String authHeader) {
        UserResponse response = authService.getCurrentUser(authHeader);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/auth/health
     * Health check endpoint for auth service.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        Map<String, String> status = new HashMap<>();
        status.put("service", "auth-service");
        status.put("status", "UP");
        status.put("jwt", "ENABLED");
        return ResponseEntity.ok(status);
    }
}
