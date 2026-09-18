package com.aegis.approval.dto;

import com.aegis.approval.enums.ApprovalPriority;
import com.aegis.approval.enums.ApprovalStatus;
import com.aegis.approval.enums.ApprovalType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApprovalResponse {

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

    // ──────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────

    public ApprovalResponse() {
    }

    public ApprovalResponse(Long id, String approvalNumber, ApprovalType approvalType,
                             ApprovalStatus status, ApprovalPriority priority, Long customerId,
                             Long claimId, String claimNumber, Long policyId, String policyNumber,
                             BigDecimal requestedAmount, BigDecimal approvedAmount,
                             String subject, String description, Long reviewerId, String reviewerName,
                             String remarks, String rejectionReason, LocalDate requestedDate,
                             LocalDate dueDate, LocalDate reviewedDate,
                             LocalDateTime createdAt, LocalDateTime updatedAt) {
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
        public Builder approvalNumber(String v) { this.approvalNumber = v; return this; }
        public Builder approvalType(ApprovalType v) { this.approvalType = v; return this; }
        public Builder status(ApprovalStatus v) { this.status = v; return this; }
        public Builder priority(ApprovalPriority v) { this.priority = v; return this; }
        public Builder customerId(Long v) { this.customerId = v; return this; }
        public Builder claimId(Long v) { this.claimId = v; return this; }
        public Builder claimNumber(String v) { this.claimNumber = v; return this; }
        public Builder policyId(Long v) { this.policyId = v; return this; }
        public Builder policyNumber(String v) { this.policyNumber = v; return this; }
        public Builder requestedAmount(BigDecimal v) { this.requestedAmount = v; return this; }
        public Builder approvedAmount(BigDecimal v) { this.approvedAmount = v; return this; }
        public Builder subject(String v) { this.subject = v; return this; }
        public Builder description(String v) { this.description = v; return this; }
        public Builder reviewerId(Long v) { this.reviewerId = v; return this; }
        public Builder reviewerName(String v) { this.reviewerName = v; return this; }
        public Builder remarks(String v) { this.remarks = v; return this; }
        public Builder rejectionReason(String v) { this.rejectionReason = v; return this; }
        public Builder requestedDate(LocalDate v) { this.requestedDate = v; return this; }
        public Builder dueDate(LocalDate v) { this.dueDate = v; return this; }
        public Builder reviewedDate(LocalDate v) { this.reviewedDate = v; return this; }
        public Builder createdAt(LocalDateTime v) { this.createdAt = v; return this; }
        public Builder updatedAt(LocalDateTime v) { this.updatedAt = v; return this; }

        public ApprovalResponse build() {
            return new ApprovalResponse(id, approvalNumber, approvalType, status, priority,
                    customerId, claimId, claimNumber, policyId, policyNumber,
                    requestedAmount, approvedAmount, subject, description,
                    reviewerId, reviewerName, remarks, rejectionReason,
                    requestedDate, dueDate, reviewedDate, createdAt, updatedAt);
        }
    }
}
