package com.aegis.claim.dto;

import com.aegis.claim.enums.ClaimStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClaimApprovalRequest {

    @NotNull(message = "Status is required")
    private ClaimStatus status;

    @DecimalMin(value = "0.00", message = "Approved amount cannot be negative")
    private BigDecimal approvedAmount;

    @Size(max = 100, message = "Stage must not exceed 100 characters")
    private String stage;

    @Size(max = 2000, message = "Rejection reason must not exceed 2000 characters")
    private String rejectionReason;

    private LocalDate resolutionDate;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDate getResolutionDate() { return resolutionDate; }
    public void setResolutionDate(LocalDate resolutionDate) { this.resolutionDate = resolutionDate; }
}
