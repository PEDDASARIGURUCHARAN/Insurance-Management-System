package com.aegis.claim.dto;

import com.aegis.claim.enums.ClaimStatus;
import jakarta.validation.constraints.NotNull;

public class ClaimStatusUpdateRequest {

    @NotNull(message = "Status is required")
    private ClaimStatus status;

    private String stage;

    private String rejectionReason;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}
