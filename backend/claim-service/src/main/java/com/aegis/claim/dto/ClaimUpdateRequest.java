package com.aegis.claim.dto;

import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import com.aegis.claim.enums.ClaimStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClaimUpdateRequest {

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private ClaimCategory category;

    private ClaimStatus status;

    private ClaimPriority priority;

    @DecimalMin(value = "0.01", message = "Claimed amount must be greater than 0")
    private BigDecimal claimedAmount;

    @DecimalMin(value = "0.00", message = "Approved amount cannot be negative")
    private BigDecimal approvedAmount;

    @Size(max = 100, message = "Stage must not exceed 100 characters")
    private String stage;

    @Size(max = 2000, message = "Rejection reason must not exceed 2000 characters")
    private String rejectionReason;

    private LocalDate incidentDate;

    private LocalDate resolutionDate;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ClaimCategory getCategory() { return category; }
    public void setCategory(ClaimCategory category) { this.category = category; }

    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }

    public ClaimPriority getPriority() { return priority; }
    public void setPriority(ClaimPriority priority) { this.priority = priority; }

    public BigDecimal getClaimedAmount() { return claimedAmount; }
    public void setClaimedAmount(BigDecimal claimedAmount) { this.claimedAmount = claimedAmount; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDate getIncidentDate() { return incidentDate; }
    public void setIncidentDate(LocalDate incidentDate) { this.incidentDate = incidentDate; }

    public LocalDate getResolutionDate() { return resolutionDate; }
    public void setResolutionDate(LocalDate resolutionDate) { this.resolutionDate = resolutionDate; }
}
