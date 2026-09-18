package com.aegis.policy.dto;

import com.aegis.policy.enums.PolicyCategory;
import com.aegis.policy.enums.PolicyStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PolicyUpdateRequest {

    @Size(max = 150, message = "Policy name must not exceed 150 characters")
    private String policyName;

    private PolicyCategory category;

    private PolicyStatus status;

    @DecimalMin(value = "0.01", message = "Coverage amount must be greater than 0")
    private BigDecimal coverageAmount;

    @DecimalMin(value = "0.00", message = "Deductible amount cannot be negative")
    private BigDecimal deductibleAmount;

    @DecimalMin(value = "0.01", message = "Premium amount must be greater than 0")
    private BigDecimal premiumAmount;

    @Size(max = 20, message = "Premium frequency must not exceed 20 characters")
    private String premiumFrequency;

    @Size(max = 100, message = "Network name must not exceed 100 characters")
    private String networkName;

    private LocalDate startDate;

    private LocalDate expiryDate;

    private String description;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public PolicyCategory getCategory() { return category; }
    public void setCategory(PolicyCategory category) { this.category = category; }

    public PolicyStatus getStatus() { return status; }
    public void setStatus(PolicyStatus status) { this.status = status; }

    public BigDecimal getCoverageAmount() { return coverageAmount; }
    public void setCoverageAmount(BigDecimal coverageAmount) { this.coverageAmount = coverageAmount; }

    public BigDecimal getDeductibleAmount() { return deductibleAmount; }
    public void setDeductibleAmount(BigDecimal deductibleAmount) { this.deductibleAmount = deductibleAmount; }

    public BigDecimal getPremiumAmount() { return premiumAmount; }
    public void setPremiumAmount(BigDecimal premiumAmount) { this.premiumAmount = premiumAmount; }

    public String getPremiumFrequency() { return premiumFrequency; }
    public void setPremiumFrequency(String premiumFrequency) { this.premiumFrequency = premiumFrequency; }

    public String getNetworkName() { return networkName; }
    public void setNetworkName(String networkName) { this.networkName = networkName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
