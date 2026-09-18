package com.aegis.claim.entity;

import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import com.aegis.claim.enums.ClaimStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "claims", indexes = {
    @Index(name = "idx_claim_customer_id", columnList = "customer_id"),
    @Index(name = "idx_claim_policy_id", columnList = "policy_id"),
    @Index(name = "idx_claim_status", columnList = "status"),
    @Index(name = "idx_claim_number", columnList = "claim_number")
})
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "claim_number", unique = true, nullable = false, length = 20)
    private String claimNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 30)
    private ClaimCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ClaimStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    private ClaimPriority priority;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "policy_id", nullable = false)
    private Long policyId;

    @Column(name = "policy_number", length = 20)
    private String policyNumber;

    @Column(name = "claimed_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal claimedAmount;

    @Column(name = "approved_amount", precision = 15, scale = 2)
    private BigDecimal approvedAmount;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "stage", length = 100)
    private String stage;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "incident_date")
    private LocalDate incidentDate;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "resolution_date")
    private LocalDate resolutionDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ──────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────

    public Claim() {
    }

    public Claim(Long id, String claimNumber, ClaimCategory category, ClaimStatus status,
                 ClaimPriority priority, Long customerId, Long policyId, String policyNumber,
                 BigDecimal claimedAmount, BigDecimal approvedAmount, String description,
                 String stage, String rejectionReason, LocalDate incidentDate,
                 LocalDate submissionDate, LocalDate resolutionDate,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.claimNumber = claimNumber;
        this.category = category;
        this.status = status;
        this.priority = priority;
        this.customerId = customerId;
        this.policyId = policyId;
        this.policyNumber = policyNumber;
        this.claimedAmount = claimedAmount;
        this.approvedAmount = approvedAmount;
        this.description = description;
        this.stage = stage;
        this.rejectionReason = rejectionReason;
        this.incidentDate = incidentDate;
        this.submissionDate = submissionDate;
        this.resolutionDate = resolutionDate;
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
            this.status = ClaimStatus.SUBMITTED;
        }
        if (this.priority == null) {
            this.priority = ClaimPriority.NORMAL;
        }
        if (this.submissionDate == null) {
            this.submissionDate = LocalDate.now();
        }
        if (this.stage == null) {
            this.stage = "Initial Review";
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

    public String getClaimNumber() { return claimNumber; }
    public void setClaimNumber(String claimNumber) { this.claimNumber = claimNumber; }

    public ClaimCategory getCategory() { return category; }
    public void setCategory(ClaimCategory category) { this.category = category; }

    public ClaimStatus getStatus() { return status; }
    public void setStatus(ClaimStatus status) { this.status = status; }

    public ClaimPriority getPriority() { return priority; }
    public void setPriority(ClaimPriority priority) { this.priority = priority; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public BigDecimal getClaimedAmount() { return claimedAmount; }
    public void setClaimedAmount(BigDecimal claimedAmount) { this.claimedAmount = claimedAmount; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStage() { return stage; }
    public void setStage(String stage) { this.stage = stage; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDate getIncidentDate() { return incidentDate; }
    public void setIncidentDate(LocalDate incidentDate) { this.incidentDate = incidentDate; }

    public LocalDate getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDate submissionDate) { this.submissionDate = submissionDate; }

    public LocalDate getResolutionDate() { return resolutionDate; }
    public void setResolutionDate(LocalDate resolutionDate) { this.resolutionDate = resolutionDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ──────────────────────────────────────────
    // Builder
    // ──────────────────────────────────────────

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String claimNumber;
        private ClaimCategory category;
        private ClaimStatus status;
        private ClaimPriority priority;
        private Long customerId;
        private Long policyId;
        private String policyNumber;
        private BigDecimal claimedAmount;
        private BigDecimal approvedAmount;
        private String description;
        private String stage;
        private String rejectionReason;
        private LocalDate incidentDate;
        private LocalDate submissionDate;
        private LocalDate resolutionDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder claimNumber(String claimNumber) { this.claimNumber = claimNumber; return this; }
        public Builder category(ClaimCategory category) { this.category = category; return this; }
        public Builder status(ClaimStatus status) { this.status = status; return this; }
        public Builder priority(ClaimPriority priority) { this.priority = priority; return this; }
        public Builder customerId(Long customerId) { this.customerId = customerId; return this; }
        public Builder policyId(Long policyId) { this.policyId = policyId; return this; }
        public Builder policyNumber(String policyNumber) { this.policyNumber = policyNumber; return this; }
        public Builder claimedAmount(BigDecimal claimedAmount) { this.claimedAmount = claimedAmount; return this; }
        public Builder approvedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder stage(String stage) { this.stage = stage; return this; }
        public Builder rejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; return this; }
        public Builder incidentDate(LocalDate incidentDate) { this.incidentDate = incidentDate; return this; }
        public Builder submissionDate(LocalDate submissionDate) { this.submissionDate = submissionDate; return this; }
        public Builder resolutionDate(LocalDate resolutionDate) { this.resolutionDate = resolutionDate; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Claim build() {
            return new Claim(id, claimNumber, category, status, priority, customerId,
                    policyId, policyNumber, claimedAmount, approvedAmount, description,
                    stage, rejectionReason, incidentDate, submissionDate, resolutionDate,
                    createdAt, updatedAt);
        }
    }
}
