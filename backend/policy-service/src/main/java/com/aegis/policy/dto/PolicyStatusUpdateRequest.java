package com.aegis.policy.dto;

import com.aegis.policy.enums.PolicyStatus;
import jakarta.validation.constraints.NotNull;

public class PolicyStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private PolicyStatus status;

    public PolicyStatus getStatus() { return status; }
    public void setStatus(PolicyStatus status) { this.status = status; }
}
