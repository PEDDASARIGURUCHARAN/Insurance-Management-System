package com.aegis.approval.dto;

import com.aegis.approval.enums.ApprovalPriority;
import com.aegis.approval.enums.ApprovalType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApprovalRequest {

    @NotNull(message = "Approval type is required")
    private ApprovalType approvalType;

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be a positive number")
    private Long customerId;

    private Long claimId;

    @Size(max = 25, message = "Claim number must not exceed 25 characters")
    private String claimNumber;

    private Long policyId;

    @Size(max = 25, message = "Policy number must not exceed 25 characters")
    private String policyNumber;

    @DecimalMin(value = "0.00", message = "Requested amount cannot be negative")
    private BigDecimal requestedAmount;

    @NotBlank(message = "Subject is required")
    @Size(max = 200, message = "Subject must not exceed 200 characters")
    private String subject;

    @Size(max = 3000, message = "Description must not exceed 3000 characters")
    private String description;

    private ApprovalPriority priority;

    private LocalDate dueDate;

    // ──────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────

    public ApprovalType getApprovalType() { return approvalType; }
    public void setApprovalType(ApprovalType approvalType) { this.approvalType = approvalType; }

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

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ApprovalPriority getPriority() { return priority; }
    public void setPriority(ApprovalPriority priority) { this.priority = priority; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
