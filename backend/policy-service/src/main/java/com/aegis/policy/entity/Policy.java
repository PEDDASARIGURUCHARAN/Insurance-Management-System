package com.aegis.policy.entity;

import com.aegis.policy.enums.PolicyCategory;
import com.aegis.policy.enums.PolicyStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "policies", indexes = {
    @Index(name = "idx_policy_customer_id", columnList = "customer_id"),
    @Index(name = "idx_policy_status", columnList = "status"),
    @Index(name = "idx_policy_category", columnList = "category"),
    @Index(name = "idx_policy_number", columnList = "policy_number")
})
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_number", unique = true, nullable = false, length = 20)
    private String policyNumber;

    @Column(name = "policy_name", nullable = false, length = 150)
    private String policyName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 30)
    private PolicyCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PolicyStatus status;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "coverage_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal coverageAmount;

    @Column(name = "deductible_amount", precision = 15, scale = 2)
    private BigDecimal deductibleAmount;

    @Column(name = "premium_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal premiumAmount;

    @Column(name = "premium_frequency", length = 20)
    private String premiumFrequency;

    @Column(name = "network_name", length = 100)
    private String networkName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ──────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────

    public Policy() {
    }

    public Policy(Long id, String policyNumber, String policyName, PolicyCategory category,
                  PolicyStatus status, Long customerId, BigDecimal coverageAmount,
                  BigDecimal deductibleAmount, BigDecimal premiumAmount, String premiumFrequency,
                  String networkName, LocalDate startDate, LocalDate expiryDate,
                  String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.policyNumber = policyNumber;
        this.policyName = policyName;
        this.category = category;
        this.status = status;
        this.customerId = customerId;
        this.coverageAmount = coverageAmount;
        this.deductibleAmount = deductibleAmount;
        this.premiumAmount = premiumAmount;
        this.premiumFrequency = premiumFrequency;
        this.networkName = networkName;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // ──────────────────────────────────────────
    // Lifecycle hooks
    // ──────────────────────────────────────────

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = PolicyStatus.ACTIVE;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public String getPolicyName() { return policyName; }
    public void setPolicyName(String policyName) { this.policyName = policyName; }

    public PolicyCategory getCategory() { return category; }
    public void setCategory(PolicyCategory category) { this.category = category; }

    public PolicyStatus getStatus() { return status; }
    public void setStatus(PolicyStatus status) { this.status = status; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ──────────────────────────────────────────
    // Builder (static inner class)
    // ──────────────────────────────────────────

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String policyNumber;
        private String policyName;
        private PolicyCategory category;
        private PolicyStatus status;
        private Long customerId;
        private BigDecimal coverageAmount;
        private BigDecimal deductibleAmount;
        private BigDecimal premiumAmount;
        private String premiumFrequency;
        private String networkName;
        private LocalDate startDate;
        private LocalDate expiryDate;
        private String description;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder policyNumber(String policyNumber) { this.policyNumber = policyNumber; return this; }
        public Builder policyName(String policyName) { this.policyName = policyName; return this; }
        public Builder category(PolicyCategory category) { this.category = category; return this; }
        public Builder status(PolicyStatus status) { this.status = status; return this; }
        public Builder customerId(Long customerId) { this.customerId = customerId; return this; }
        public Builder coverageAmount(BigDecimal coverageAmount) { this.coverageAmount = coverageAmount; return this; }
        public Builder deductibleAmount(BigDecimal deductibleAmount) { this.deductibleAmount = deductibleAmount; return this; }
        public Builder premiumAmount(BigDecimal premiumAmount) { this.premiumAmount = premiumAmount; return this; }
        public Builder premiumFrequency(String premiumFrequency) { this.premiumFrequency = premiumFrequency; return this; }
        public Builder networkName(String networkName) { this.networkName = networkName; return this; }
        public Builder startDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public Builder expiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Policy build() {
            return new Policy(id, policyNumber, policyName, category, status, customerId,
                    coverageAmount, deductibleAmount, premiumAmount, premiumFrequency,
                    networkName, startDate, expiryDate, description, createdAt, updatedAt);
        }
    }
}
