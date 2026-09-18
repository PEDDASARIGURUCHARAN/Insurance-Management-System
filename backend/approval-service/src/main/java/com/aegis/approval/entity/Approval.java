package com.aegis.approval.entity;

import com.aegis.approval.enums.ApprovalPriority;
import com.aegis.approval.enums.ApprovalStatus;
import com.aegis.approval.enums.ApprovalType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "approvals", indexes = {
    @Index(name = "idx_approval_customer_id", columnList = "customer_id"),
    @Index(name = "idx_approval_claim_id", columnList = "claim_id"),
    @Index(name = "idx_approval_status", columnList = "status"),
    @Index(name = "idx_approval_number", columnList = "approval_number")
})
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "approval_number", unique = true, nullable = false, length = 25)
    private String approvalNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_type", nullable = false, length = 30)
    private ApprovalType approvalType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ApprovalStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false, length = 10)
    private ApprovalPriority priority;

    // ── Reference IDs (cross-service IDs; no FK constraints)
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "claim_id")
    private Long claimId;

    @Column(name = "claim_number", length = 25)
    private String claimNumber;

    @Column(name = "policy_id")
    private Long policyId;

    @Column(name = "policy_number", length = 25)
    private String policyNumber;

    // ── Financial fields
    @Column(name = "requested_amount", precision = 15, scale = 2)
    private BigDecimal requestedAmount;

    @Column(name = "approved_amount", precision = 15, scale = 2)
    private BigDecimal approvedAmount;

    // ── Workflow fields
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "reviewer_name", length = 100)
    private String reviewerName;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    // ── Dates
    @Column(name = "requested_date")
    private LocalDate requestedDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "reviewed_date")
    private LocalDate reviewedDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ──────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────

    public Approval() {
    }

    public Approval(Long id, String approvalNumber, ApprovalType approvalType, ApprovalStatus status,
                    ApprovalPriority priority, Long customerId, Long claimId, String claimNumber,
                    Long policyId, String policyNumber, BigDecimal requestedAmount,
                    BigDecimal approvedAmount, String subject, String description,
                    Long reviewerId, String reviewerName, String remarks,
                    String rejectionReason, LocalDate requestedDate, LocalDate dueDate,
                    LocalDate reviewedDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.approvalNumber = approvalNumber;
        this.approvalType = approvalType;
        this.status = status;
        this.priority = priority;
        this.customerId = customerId;
        this.claimId = claimId;
        this.claimNumber = claimNumber;
        this.policyId = policyId;
        this.policyNumber = policyNumber;
        this.requestedAmount = requestedAmount;
        this.approvedAmount = approvedAmount;
        this.subject = subject;
        this.description = description;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.remarks = remarks;
        this.rejectionReason = rejectionReason;
        this.requestedDate = requestedDate;
        this.dueDate = dueDate;
        this.reviewedDate = reviewedDate;
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
            this.status = ApprovalStatus.PENDING;
        }
        if (this.priority == null) {
            this.priority = ApprovalPriority.NORMAL;
        }
        if (this.requestedDate == null) {
            this.requestedDate = LocalDate.now();
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

    public String getApprovalNumber() { return approvalNumber; }
    public void setApprovalNumber(String approvalNumber) { this.approvalNumber = approvalNumber; }

    public ApprovalType getApprovalType() { return approvalType; }
    public void setApprovalType(ApprovalType approvalType) { this.approvalType = approvalType; }

    public ApprovalStatus getStatus() { return status; }
    public void setStatus(ApprovalStatus status) { this.status = status; }

    public ApprovalPriority getPriority() { return priority; }
    public void setPriority(ApprovalPriority priority) { this.priority = priority; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getClaimId() { return claimId; }
    public void setClaimId(Long claimId) { this.claimId = claimId; }

    public String getClaimNumber() { return claimNumber; }
    public void setClaimNumber(String claimNumber) { this.claimNumber = claimNumber; }

    public Long getPolicyId() { return policyId; }
    public void setPolicyId(Long policyId) { this.policyId = policyId; }

    public String getPolicyNumber() { return policyNumber; }
    public void setPolicyNumber(String policyNumber) { this.policyNumber = policyNumber; }

    public BigDecimal getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(BigDecimal requestedAmount) { this.requestedAmount = requestedAmount; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getReviewerId() { return reviewerId; }
    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public LocalDate getRequestedDate() { return requestedDate; }
    public void setRequestedDate(LocalDate requestedDate) { this.requestedDate = requestedDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReviewedDate() { return reviewedDate; }
    public void setReviewedDate(LocalDate reviewedDate) { this.reviewedDate = reviewedDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // ──────────────────────────────────────────
    // Builder
    // ──────────────────────────────────────────

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Long id;
        private String approvalNumber;
        private ApprovalType approvalType;
        private ApprovalStatus status;
        private ApprovalPriority priority;
        private Long customerId;
        private Long claimId;
        private String claimNumber;
        private Long policyId;
        private String policyNumber;
        private BigDecimal requestedAmount;
        private BigDecimal approvedAmount;
        private String subject;
        private String description;
        private Long reviewerId;
        private String reviewerName;
        private String remarks;
        private String rejectionReason;
        private LocalDate requestedDate;
        private LocalDate dueDate;
        private LocalDate reviewedDate;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder approvalNumber(String approvalNumber) { this.approvalNumber = approvalNumber; return this; }
        public Builder approvalType(ApprovalType approvalType) { this.approvalType = approvalType; return this; }
        public Builder status(ApprovalStatus status) { this.status = status; return this; }
        public Builder priority(ApprovalPriority priority) { this.priority = priority; return this; }
        public Builder customerId(Long customerId) { this.customerId = customerId; return this; }
        public Builder claimId(Long claimId) { this.claimId = claimId; return this; }
        public Builder claimNumber(String claimNumber) { this.claimNumber = claimNumber; return this; }
        public Builder policyId(Long policyId) { this.policyId = policyId; return this; }
        public Builder policyNumber(String policyNumber) { this.policyNumber = policyNumber; return this; }
        public Builder requestedAmount(BigDecimal requestedAmount) { this.requestedAmount = requestedAmount; return this; }
        public Builder approvedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; return this; }
        public Builder subject(String subject) { this.subject = subject; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder reviewerId(Long reviewerId) { this.reviewerId = reviewerId; return this; }
        public Builder reviewerName(String reviewerName) { this.reviewerName = reviewerName; return this; }
        public Builder remarks(String remarks) { this.remarks = remarks; return this; }
        public Builder rejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; return this; }
        public Builder requestedDate(LocalDate requestedDate) { this.requestedDate = requestedDate; return this; }
        public Builder dueDate(LocalDate dueDate) { this.dueDate = dueDate; return this; }
        public Builder reviewedDate(LocalDate reviewedDate) { this.reviewedDate = reviewedDate; return this; }
        public Builder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public Builder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Approval build() {
            return new Approval(id, approvalNumber, approvalType, status, priority,
                    customerId, claimId, claimNumber, policyId, policyNumber,
                    requestedAmount, approvedAmount, subject, description,
                    reviewerId, reviewerName, remarks, rejectionReason,
                    requestedDate, dueDate, reviewedDate, createdAt, updatedAt);
        }
    }
}
