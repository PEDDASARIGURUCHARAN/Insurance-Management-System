package com.aegis.auth.dto;

public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private String role;
    private String expire = "24hrs";

    public AuthResponse() {
    }

    public AuthResponse(String token, String type, String username, String role, String expire) {
        this.token = token;
        this.type = type != null ? type : "Bearer";
        this.username = username;
        this.role = role;
        this.expire = expire != null ? expire : "24hrs";
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getExpire() { return expire; }
    public void setExpire(String expire) { this.expire = expire; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private String type = "Bearer";
        private String username;
        private String role;
        private String expire = "24hrs";

        public Builder token(String token) { this.token = token; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder role(String role) { this.role = role; return this; }
        public Builder expire(String expire) { this.expire = expire; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, type, username, role, expire);
        }
    }
}
