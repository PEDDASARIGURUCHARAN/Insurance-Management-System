package com.aegis.auth.service;

import com.aegis.auth.dto.*;
import com.aegis.auth.entity.User;
import com.aegis.auth.enums.Role;
import com.aegis.auth.exception.InvalidCredentialsException;
import com.aegis.auth.exception.UserAlreadyExistsException;
import com.aegis.auth.repository.UserRepository;
import com.aegis.auth.security.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse login(LoginRequest request) {
        String identifier = request.getLoginId().trim();

        User user = userRepository.findByLoginIdOrEmail(identifier, identifier)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid login ID/email or password"));

        if (!user.isEnabled()) {
            throw new InvalidCredentialsException("Account is deactivated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid login ID/email or password");
        }

        String token = jwtUtils.generateToken(user);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .id(user.getId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .expiresIn(jwtUtils.getJwtExpirationMs())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new UserAlreadyExistsException("Login ID is already taken: " + request.getLoginId());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email is already registered: " + request.getEmail());
        }

        Role role = request.getRole() != null ? request.getRole() : Role.CUSTOMER;

        User user = User.builder()
                .loginId(request.getLoginId().trim())
                .name(request.getName().trim())
                .email(request.getEmail().trim().toLowerCase())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .phone(request.getPhone())
                .enabled(true)
                .build();

        User saved = userRepository.save(user);
        String token = jwtUtils.generateToken(saved);

        return AuthResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .id(saved.getId())
                .loginId(saved.getLoginId())
                .name(saved.getName())
                .email(saved.getEmail())
                .role(saved.getRole())
                .expiresIn(jwtUtils.getJwtExpirationMs())
                .build();
    }

    @Transactional(readOnly = true)
    public ValidateTokenResponse validateToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (token == null || !jwtUtils.validateToken(token)) {
            return ValidateTokenResponse.invalid("Invalid or expired JWT token");
        }

        try {
            Claims claims = jwtUtils.extractAllClaims(token);
            String loginId = claims.getSubject();
            Long userId = claims.get("userId", Long.class);
            String name = claims.get("name", String.class);
            String email = claims.get("email", String.class);
            String roleStr = claims.get("role", String.class);
            Role role = roleStr != null ? Role.valueOf(roleStr) : Role.CUSTOMER;

            return ValidateTokenResponse.valid(userId, loginId, name, email, role);
        } catch (Exception e) {
            return ValidateTokenResponse.invalid("Failed to parse token claims: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!jwtUtils.validateToken(token)) {
            throw new InvalidCredentialsException("Invalid or expired JWT token");
        }

        String loginId = jwtUtils.getLoginIdFromToken(token);
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new InvalidCredentialsException("User not found with login ID: " + loginId));

        return new UserResponse(
                user.getId(),
                user.getLoginId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getPhone(),
                user.isEnabled(),
                user.getCreatedAt()
        );
    }
}
