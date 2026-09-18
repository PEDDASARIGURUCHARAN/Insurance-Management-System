package com.aegis.approval.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ApprovalRemarksRequest {

    @NotBlank(message = "Remarks are required")
    @Size(max = 3000, message = "Remarks must not exceed 3000 characters")
    private String remarks;

    private Long reviewerId;

    @Size(max = 100, message = "Reviewer name must not exceed 100 characters")
    private String reviewerName;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Long getReviewerId() { return reviewerId; }
    public void setReviewerId(Long reviewerId) { this.reviewerId = reviewerId; }

    public String getReviewerName() { return reviewerName; }
    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
}
