package com.aegis.auth.dto;

import com.aegis.auth.enums.Role;

public class ValidateTokenResponse {

    private boolean valid;
    private Long userId;
    private String loginId;
    private String name;
    private String email;
    private Role role;
    private String message;

    public ValidateTokenResponse() {
    }

    public ValidateTokenResponse(boolean valid, Long userId, String loginId, String name, String email, Role role, String message) {
        this.valid = valid;
        this.userId = userId;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getLoginId() { return loginId; }
    public void setLoginId(String loginId) { this.loginId = loginId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public static ValidateTokenResponse valid(Long userId, String loginId, String name, String email, Role role) {
        return new ValidateTokenResponse(true, userId, loginId, name, email, role, "Token is valid");
    }

    public static ValidateTokenResponse invalid(String message) {
        return new ValidateTokenResponse(false, null, null, null, null, null, message);
    }
}
