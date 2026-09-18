package com.aegis.auth.dto;

import com.aegis.auth.enums.Role;

public class AuthResponse {

    private String token;
    private String tokenType = "Bearer";
    private Long id;
    private String loginId;
    private String name;
    private String email;
    private Role role;
    private long expiresIn;

    public AuthResponse() {
    }

    public AuthResponse(String token, String tokenType, Long id, String loginId, String name, String email, Role role, long expiresIn) {
        this.token = token;
        this.tokenType = tokenType;
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.expiresIn = expiresIn;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public long getExpiresIn() { return expiresIn; }
    public void setExpiresIn(long expiresIn) { this.expiresIn = expiresIn; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private String tokenType = "Bearer";
        private Long id;
        private String loginId;
        private String name;
        private String email;
        private Role role;
        private long expiresIn;

        public Builder token(String token) { this.token = token; return this; }
        public Builder tokenType(String tokenType) { this.tokenType = tokenType; return this; }
        public Builder id(Long id) { this.id = id; return this; }
        public Builder loginId(String loginId) { this.loginId = loginId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder role(Role role) { this.role = role; return this; }
        public Builder expiresIn(long expiresIn) { this.expiresIn = expiresIn; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, tokenType, id, loginId, name, email, role, expiresIn);
        }
    }
}
