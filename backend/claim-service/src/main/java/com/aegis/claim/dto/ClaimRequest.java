package com.aegis.claim.dto;

import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClaimRequest {

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be a positive number")
    private Long customerId;

    @NotNull(message = "Policy ID is required")
    @Positive(message = "Policy ID must be a positive number")
    private Long policyId;

    @Size(max = 20, message = "Policy number must not exceed 20 characters")
    private String policyNumber;

    @NotNull(message = "Category is required")
    private ClaimCategory category;

    private ClaimPriority priority;

    @NotNull(message = "Claimed amount is required")
    @DecimalMin(value = "0.01", message = "Claimed amount must be greater than 0")
    private BigDecimal claimedAmount;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private LocalDate incidentDate;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public ClaimCategory getCategory() { return category; }
    public void setCategory(ClaimCategory category) { this.category = category; }

    public ClaimPriority getPriority() { return priority; }
    public void setPriority(ClaimPriority priority) { this.priority = priority; }

    public BigDecimal getClaimedAmount() { return claimedAmount; }
    public void setClaimedAmount(BigDecimal claimedAmount) { this.claimedAmount = claimedAmount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getIncidentDate() { return incidentDate; }
    public void setIncidentDate(LocalDate incidentDate) { this.incidentDate = incidentDate; }
}
