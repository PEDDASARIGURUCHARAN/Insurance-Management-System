package com.aegis.approval.dto;

import com.aegis.approval.enums.ApprovalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApprovalDecisionRequest {

    @NotNull(message = "Status is required")
    private ApprovalStatus status;

    private BigDecimal approvedAmount;

    @Size(max = 3000, message = "Remarks must not exceed 3000 characters")
    private String remarks;

    @Size(max = 3000, message = "Rejection reason must not exceed 3000 characters")
    private String rejectionReason;

    private Long reviewerId;

    @Size(max = 100, message = "Reviewer name must not exceed 100 characters")
    private String reviewerName;

    private LocalDate reviewedDate;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public ApprovalStatus getStatus() { return status; }
    public void setStatus(ApprovalStatus status) { this.status = status; }

    public BigDecimal getApprovedAmount() { return approvedAmount; }
    public void setApprovedAmount(BigDecimal approvedAmount) { this.approvedAmount = approvedAmount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public Long getReviewerId() { return reviewerId; }
    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public LocalDate getReviewedDate() { return reviewedDate; }
    public void setReviewedDate(LocalDate reviewedDate) { this.reviewedDate = reviewedDate; }
}
