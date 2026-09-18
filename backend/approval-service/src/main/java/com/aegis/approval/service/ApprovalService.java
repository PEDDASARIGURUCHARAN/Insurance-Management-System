package com.aegis.approval.service;

import com.aegis.approval.dto.*;
import com.aegis.approval.entity.Approval;
import com.aegis.approval.enums.ApprovalPriority;
import com.aegis.approval.enums.ApprovalStatus;
import com.aegis.approval.enums.ApprovalType;
import com.aegis.approval.exception.ApprovalNotFoundException;
import com.aegis.approval.repository.ApprovalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApprovalService {

    private final ApprovalRepository approvalRepository;

    public ApprovalService(ApprovalRepository approvalRepository) {
        this.approvalRepository = approvalRepository;
    }

    // ──────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────

    public ApprovalResponse createApproval(ApprovalRequest request) {
        Approval approval = Approval.builder()
                .approvalNumber(generateApprovalNumber())
                .approvalType(request.getApprovalType())
                .status(ApprovalStatus.PENDING)
                .priority(request.getPriority() != null ? request.getPriority() : ApprovalPriority.NORMAL)
                .customerId(request.getCustomerId())
                .claimId(request.getClaimId())
                .claimNumber(request.getClaimNumber())
                .policyId(request.getPolicyId())
                .policyNumber(request.getPolicyNumber())
                .requestedAmount(request.getRequestedAmount())
                .subject(request.getSubject())
                .description(request.getDescription())
                .requestedDate(LocalDate.now())
                .dueDate(request.getDueDate())
                .build();

        Approval saved = approvalRepository.save(approval);
        return toResponse(saved);
    }

    // ──────────────────────────────────────────
    // READ
    // ──────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getAllApprovals() {
        return approvalRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApprovalResponse getApprovalById(Long id) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new ApprovalNotFoundException(id));
        return toResponse(approval);
    }

    @Transactional(readOnly = true)
    public ApprovalResponse getApprovalByNumber(String approvalNumber) {
        Approval approval = approvalRepository.findByApprovalNumber(approvalNumber)
                .orElseThrow(() -> new ApprovalNotFoundException("approval number", approvalNumber));
        return toResponse(approval);
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByCustomer(Long customerId) {
        return approvalRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByClaim(Long claimId) {
        return approvalRepository.findByClaimId(claimId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByPolicy(Long policyId) {
        return approvalRepository.findByPolicyId(policyId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByStatus(ApprovalStatus status) {
        return approvalRepository.findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByType(ApprovalType approvalType) {
        return approvalRepository.findByApprovalType(approvalType)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByPriority(ApprovalPriority priority) {
        return approvalRepository.findByPriority(priority)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByReviewer(Long reviewerId) {
        return approvalRepository.findByReviewerId(reviewerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> getApprovalsByCustomerAndStatus(Long customerId, ApprovalStatus status) {
        return approvalRepository.findByCustomerIdAndStatus(customerId, status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────
    // UPDATE — General
    // ──────────────────────────────────────────

    public ApprovalResponse updateApproval(Long id, ApprovalUpdateRequest request) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new ApprovalNotFoundException(id));

        if (request.getSubject() != null) {
            approval.setSubject(request.getSubject());
        }
        if (request.getDescription() != null) {
            approval.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            approval.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            approval.setPriority(request.getPriority());
        }
        if (request.getRequestedAmount() != null) {
            approval.setRequestedAmount(request.getRequestedAmount());
        }
        if (request.getApprovedAmount() != null) {
            approval.setApprovedAmount(request.getApprovedAmount());
        }
        if (request.getRemarks() != null) {
            approval.setRemarks(request.getRemarks());
        }
        if (request.getRejectionReason() != null) {
            approval.setRejectionReason(request.getRejectionReason());
        }
        if (request.getReviewerId() != null) {
            approval.setReviewerId(request.getReviewerId());
        }
        if (request.getReviewerName() != null) {
            approval.setReviewerName(request.getReviewerName());
        }
        if (request.getDueDate() != null) {
            approval.setDueDate(request.getDueDate());
        }
        if (request.getReviewedDate() != null) {
            approval.setReviewedDate(request.getReviewedDate());
        }

        Approval updated = approvalRepository.save(approval);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // UPDATE — Decision (Approve / Reject)
    // ──────────────────────────────────────────

    public ApprovalResponse makeDecision(Long id, ApprovalDecisionRequest request) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new ApprovalNotFoundException(id));

        // Guard: cannot re-decide a terminal approval
        if (approval.getStatus() == ApprovalStatus.APPROVED
                || approval.getStatus() == ApprovalStatus.REJECTED
                || approval.getStatus() == ApprovalStatus.WITHDRAWN) {
            throw new IllegalStateException(
                    "Cannot change decision on an approval that is already "
                            + approval.getStatus().name().toLowerCase());
        }

        approval.setStatus(request.getStatus());

        if (request.getApprovedAmount() != null) {
            approval.setApprovedAmount(request.getApprovedAmount());
        }
        if (request.getRemarks() != null) {
            approval.setRemarks(request.getRemarks());
        }
        if (request.getRejectionReason() != null) {
            approval.setRejectionReason(request.getRejectionReason());
        }
        if (request.getReviewerId() != null) {
            approval.setReviewerId(request.getReviewerId());
        }
        if (request.getReviewerName() != null) {
            approval.setReviewerName(request.getReviewerName());
        }
        // Auto-set reviewed date if not provided
        LocalDate reviewedOn = request.getReviewedDate() != null ? request.getReviewedDate() : LocalDate.now();
        approval.setReviewedDate(reviewedOn);

        Approval updated = approvalRepository.save(approval);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // UPDATE — Add Remarks
    // ──────────────────────────────────────────

    public ApprovalResponse addRemarks(Long id, ApprovalRemarksRequest request) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new ApprovalNotFoundException(id));

        approval.setRemarks(request.getRemarks());
        if (request.getReviewerId() != null) {
            approval.setReviewerId(request.getReviewerId());
        }
        if (request.getReviewerName() != null) {
            approval.setReviewerName(request.getReviewerName());
        }

        // Move to UNDER_REVIEW if still PENDING
        if (approval.getStatus() == ApprovalStatus.PENDING) {
            approval.setStatus(ApprovalStatus.UNDER_REVIEW);
        }

        Approval updated = approvalRepository.save(approval);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // WITHDRAW / DELETE
    // ──────────────────────────────────────────

    public ApprovalResponse withdrawApproval(Long id) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new ApprovalNotFoundException(id));

        if (approval.getStatus() == ApprovalStatus.APPROVED
                || approval.getStatus() == ApprovalStatus.REJECTED) {
            throw new IllegalStateException(
                    "Cannot withdraw an approval that has already been decided");
        }

        approval.setStatus(ApprovalStatus.WITHDRAWN);
        Approval updated = approvalRepository.save(approval);
        return toResponse(updated);
    }

    public void deleteApproval(Long id) {
        if (!approvalRepository.existsById(id)) {
            throw new ApprovalNotFoundException(id);
        }
        approvalRepository.deleteById(id);
    }

    // ──────────────────────────────────────────
    // PRIVATE HELPERS
    // ──────────────────────────────────────────

    private ApprovalResponse toResponse(Approval a) {
        return ApprovalResponse.builder()
                .id(a.getId())
                .approvalNumber(a.getApprovalNumber())
                .approvalType(a.getApprovalType())
                .status(a.getStatus())
                .priority(a.getPriority())
                .customerId(a.getCustomerId())
                .claimId(a.getClaimId())
                .claimNumber(a.getClaimNumber())
                .policyId(a.getPolicyId())
                .policyNumber(a.getPolicyNumber())
                .requestedAmount(a.getRequestedAmount())
                .approvedAmount(a.getApprovedAmount())
                .subject(a.getSubject())
                .description(a.getDescription())
                .reviewerId(a.getReviewerId())
                .reviewerName(a.getReviewerName())
                .remarks(a.getRemarks())
                .rejectionReason(a.getRejectionReason())
                .requestedDate(a.getRequestedDate())
                .dueDate(a.getDueDate())
                .reviewedDate(a.getReviewedDate())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }

    private String generateApprovalNumber() {
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String random = String.format("%05d", (int) (Math.random() * 100000));
        String candidate = "APR-" + yearMonth + "-" + random;
        while (approvalRepository.existsByApprovalNumber(candidate)) {
            random = String.format("%05d", (int) (Math.random() * 100000));
            candidate = "APR-" + yearMonth + "-" + random;
        }
        return candidate;
    }
}
