package com.aegis.claim.service;

import com.aegis.claim.dto.*;
import com.aegis.claim.entity.Claim;
import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import com.aegis.claim.enums.ClaimStatus;
import com.aegis.claim.exception.ClaimNotFoundException;
import com.aegis.claim.repository.ClaimRepository;
import com.aegis.claim.client.ApprovalClient;
import com.aegis.claim.client.PolicyClient;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClaimService {

    private static final Logger log = Logger.getLogger(ClaimService.class.getName());

    private final ClaimRepository claimRepository;
    private final PolicyClient policyClient;
    private final ApprovalClient approvalClient;

    public ClaimService(ClaimRepository claimRepository,
                        PolicyClient policyClient,
                        ApprovalClient approvalClient) {
        this.claimRepository = claimRepository;
        this.policyClient = policyClient;
        this.approvalClient = approvalClient;
    }

    // ──────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────

    public ClaimResponse submitClaim(ClaimRequest request) {
        String resolvedPolicyNumber = request.getPolicyNumber();

        // 1. Inter-Service Communication: Validate Policy via PolicyClient
        try {
            PolicyDto policy = null;
            if (request.getPolicyId() != null) {
                policy = policyClient.getPolicyById(request.getPolicyId());
            } else if (request.getPolicyNumber() != null) {
                policy = policyClient.getPolicyByNumber(request.getPolicyNumber());
            }

            if (policy != null) {
                if (resolvedPolicyNumber == null || resolvedPolicyNumber.isBlank()) {
                    resolvedPolicyNumber = policy.getPolicyNumber();
                }
                log.info("Successfully validated policy " + policy.getPolicyNumber() + " via Policy Service");
            }
        } catch (Exception e) {
            log.warning("Policy Service validation skipped or unavailable: " + e.getMessage());
        }

        Claim claim = Claim.builder()
                .claimNumber(generateClaimNumber())
                .customerId(request.getCustomerId())
                .policyId(request.getPolicyId())
                .policyNumber(resolvedPolicyNumber)
                .category(request.getCategory())
                .priority(request.getPriority() != null ? request.getPriority() : ClaimPriority.NORMAL)
                .status(ClaimStatus.SUBMITTED)
                .claimedAmount(request.getClaimedAmount())
                .description(request.getDescription())
                .incidentDate(request.getIncidentDate())
                .submissionDate(LocalDate.now())
                .stage("Initial Review")
                .build();

        Claim saved = claimRepository.save(claim);

        // 2. Inter-Service Communication: Trigger Approval workflow via ApprovalClient
        try {
            ApprovalClientDto approvalReq = new ApprovalClientDto();
            approvalReq.setApprovalType("CLAIM_SETTLEMENT");
            approvalReq.setCustomerId(saved.getCustomerId());
            approvalReq.setClaimId(saved.getId());
            approvalReq.setClaimNumber(saved.getClaimNumber());
            approvalReq.setPolicyId(saved.getPolicyId());
            approvalReq.setPolicyNumber(saved.getPolicyNumber());
            approvalReq.setRequestedAmount(saved.getClaimedAmount());
            approvalReq.setSubject("Claim Settlement Request for " + saved.getClaimNumber());
            approvalReq.setDescription("Automated approval request created for submitted claim: " + saved.getDescription());
            approvalReq.setPriority(saved.getPriority() != null ? saved.getPriority().name() : "NORMAL");

            approvalClient.createApproval(approvalReq);
            log.info("Successfully created approval request for claim " + saved.getClaimNumber() + " via Approval Service");
        } catch (Exception e) {
            log.warning("Approval Service workflow creation skipped or unavailable: " + e.getMessage());
        }

        return toResponse(saved);
    }

    // ──────────────────────────────────────────
    // READ
    // ──────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<ClaimResponse> getAllClaims() {
        return claimRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClaimResponse getClaimById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException(id));
        return toResponse(claim);
    }

    @Transactional(readOnly = true)
    public ClaimResponse getClaimByClaimNumber(String claimNumber) {
        Claim claim = claimRepository.findByClaimNumber(claimNumber)
                .orElseThrow(() -> new ClaimNotFoundException("claim number", claimNumber));
        return toResponse(claim);
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByCustomer(Long customerId) {
        return claimRepository.findByCustomerIdOrderByCreatedAtDesc(customerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByPolicy(Long policyId) {
        return claimRepository.findByPolicyId(policyId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByStatus(ClaimStatus status) {
        return claimRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByCategory(ClaimCategory category) {
        return claimRepository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByPriority(ClaimPriority priority) {
        return claimRepository.findByPriority(priority)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByCustomerAndStatus(Long customerId, ClaimStatus status) {
        return claimRepository.findByCustomerIdAndStatus(customerId, status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByPolicyAndStatus(Long policyId, ClaimStatus status) {
        return claimRepository.findByPolicyIdAndStatus(policyId, status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────
    // UPDATE — General
    // ──────────────────────────────────────────

    public ClaimResponse updateClaim(Long id, ClaimUpdateRequest request) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException(id));

        if (request.getDescription() != null) {
            claim.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            claim.setCategory(request.getCategory());
        }
        if (request.getStatus() != null) {
            claim.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            claim.setPriority(request.getPriority());
        }
        if (request.getClaimedAmount() != null) {
            claim.setClaimedAmount(request.getClaimedAmount());
        }
        if (request.getApprovedAmount() != null) {
            claim.setApprovedAmount(request.getApprovedAmount());
        }
        if (request.getStage() != null) {
            claim.setStage(request.getStage());
        }
        if (request.getRejectionReason() != null) {
            claim.setRejectionReason(request.getRejectionReason());
        }
        if (request.getIncidentDate() != null) {
            claim.setIncidentDate(request.getIncidentDate());
        }
        if (request.getResolutionDate() != null) {
            claim.setResolutionDate(request.getResolutionDate());
        }

        Claim updated = claimRepository.save(claim);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // UPDATE — Status
    // ──────────────────────────────────────────

    public ClaimResponse updateClaimStatus(Long id, ClaimStatusUpdateRequest request) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException(id));

        claim.setStatus(request.getStatus());
        if (request.getStage() != null) {
            claim.setStage(request.getStage());
        }
        if (request.getRejectionReason() != null) {
            claim.setRejectionReason(request.getRejectionReason());
        }

        Claim updated = claimRepository.save(claim);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // UPDATE — Adjudication (Approve / Reject)
    // ──────────────────────────────────────────

    public ClaimResponse adjudicateClaim(Long id, ClaimApprovalRequest request) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException(id));

        // Only claims under review can be adjudicated
        if (claim.getStatus() == ClaimStatus.SETTLED || claim.getStatus() == ClaimStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Cannot adjudicate a claim that is already " + claim.getStatus().name().toLowerCase());
        }

        claim.setStatus(request.getStatus());

        if (request.getApprovedAmount() != null) {
            claim.setApprovedAmount(request.getApprovedAmount());
        }
        if (request.getStage() != null) {
            claim.setStage(request.getStage());
        } else {
            // Auto-set a meaningful stage based on the decision
            if (request.getStatus() == ClaimStatus.APPROVED) {
                claim.setStage("Settlement Issued");
            } else if (request.getStatus() == ClaimStatus.REJECTED) {
                claim.setStage("Claim Closed - Rejected");
            } else if (request.getStatus() == ClaimStatus.SETTLED) {
                claim.setStage("Settlement Completed");
            }
        }
        if (request.getRejectionReason() != null) {
            claim.setRejectionReason(request.getRejectionReason());
        }
        if (request.getResolutionDate() != null) {
            claim.setResolutionDate(request.getResolutionDate());
        } else if (request.getStatus() == ClaimStatus.APPROVED
                || request.getStatus() == ClaimStatus.REJECTED
                || request.getStatus() == ClaimStatus.SETTLED) {
            claim.setResolutionDate(LocalDate.now());
        }

        Claim updated = claimRepository.save(claim);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // CANCEL
    // ──────────────────────────────────────────

    public ClaimResponse cancelClaim(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ClaimNotFoundException(id));

        if (claim.getStatus() == ClaimStatus.SETTLED) {
            throw new IllegalStateException("Cannot cancel a claim that has already been settled");
        }
        claim.setStatus(ClaimStatus.CANCELLED);
        claim.setStage("Claim Cancelled");

        Claim updated = claimRepository.save(claim);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // DELETE
    // ──────────────────────────────────────────

    public void deleteClaim(Long id) {
        if (!claimRepository.existsById(id)) {
            throw new ClaimNotFoundException(id);
        }
        claimRepository.deleteById(id);
    }

    // ──────────────────────────────────────────
    // PRIVATE HELPERS
    // ──────────────────────────────────────────

    private ClaimResponse toResponse(Claim claim) {
        return ClaimResponse.builder()
                .id(claim.getId())
                .claimNumber(claim.getClaimNumber())
                .category(claim.getCategory())
                .status(claim.getStatus())
                .priority(claim.getPriority())
                .customerId(claim.getCustomerId())
                .policyId(claim.getPolicyId())
                .policyNumber(claim.getPolicyNumber())
                .claimedAmount(claim.getClaimedAmount())
                .approvedAmount(claim.getApprovedAmount())
                .description(claim.getDescription())
                .stage(claim.getStage())
                .rejectionReason(claim.getRejectionReason())
                .incidentDate(claim.getIncidentDate())
                .submissionDate(claim.getSubmissionDate())
                .resolutionDate(claim.getResolutionDate())
                .createdAt(claim.getCreatedAt())
                .updatedAt(claim.getUpdatedAt())
                .build();
    }

    private String generateClaimNumber() {
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String random = String.format("%05d", (int) (Math.random() * 100000));
        String candidate = "CLM-" + yearMonth + "-" + random;
        while (claimRepository.existsByClaimNumber(candidate)) {
            random = String.format("%05d", (int) (Math.random() * 100000));
            candidate = "CLM-" + yearMonth + "-" + random;
        }
        return candidate;
    }
}
