package com.aegis.policy.service;

import com.aegis.policy.dto.PolicyRequest;
import com.aegis.policy.dto.PolicyResponse;
import com.aegis.policy.dto.PolicyStatusUpdateRequest;
import com.aegis.policy.dto.PolicyUpdateRequest;
import com.aegis.policy.entity.Policy;
import com.aegis.policy.enums.PolicyCategory;
import com.aegis.policy.enums.PolicyStatus;
import com.aegis.policy.exception.PolicyNotFoundException;
import com.aegis.policy.repository.PolicyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    // ──────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────

    public PolicyResponse createPolicy(PolicyRequest request) {
        if (request.getExpiryDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("Expiry date must be after start date");
        }

        Policy policy = Policy.builder()
                .policyNumber(generatePolicyNumber())
                .policyName(request.getPolicyName())
                .category(request.getCategory())
                .status(PolicyStatus.ACTIVE)
                .customerId(request.getCustomerId())
                .coverageAmount(request.getCoverageAmount())
                .deductibleAmount(request.getDeductibleAmount())
                .premiumAmount(request.getPremiumAmount())
                .premiumFrequency(request.getPremiumFrequency())
                .networkName(request.getNetworkName())
                .startDate(request.getStartDate())
                .expiryDate(request.getExpiryDate())
                .description(request.getDescription())
                .build();

        Policy saved = policyRepository.save(policy);
        return toResponse(saved);
    }

    // ──────────────────────────────────────────
    // READ
    // ──────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<PolicyResponse> getAllPolicies() {
        return policyRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PolicyResponse getPolicyById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException(id));
        return toResponse(policy);
    }

    @Transactional(readOnly = true)
    public PolicyResponse getPolicyByPolicyNumber(String policyNumber) {
        Policy policy = policyRepository.findByPolicyNumber(policyNumber)
                .orElseThrow(() -> new PolicyNotFoundException("policy number", policyNumber));
        return toResponse(policy);
    }

    @Transactional(readOnly = true)
    public List<PolicyResponse> getPoliciesByCustomer(Long customerId) {
        return policyRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PolicyResponse> getPoliciesByStatus(PolicyStatus status) {
        return policyRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PolicyResponse> getPoliciesByCategory(PolicyCategory category) {
        return policyRepository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PolicyResponse> getPoliciesByCustomerAndStatus(Long customerId, PolicyStatus status) {
        return policyRepository.findByCustomerIdAndStatus(customerId, status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ──────────────────────────────────────────
    // UPDATE
    // ──────────────────────────────────────────

    public PolicyResponse updatePolicy(Long id, PolicyUpdateRequest request) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException(id));

        if (request.getPolicyName() != null) {
            policy.setPolicyName(request.getPolicyName());
        }
        if (request.getCategory() != null) {
            policy.setCategory(request.getCategory());
        }
        if (request.getStatus() != null) {
            policy.setStatus(request.getStatus());
        }
        if (request.getCoverageAmount() != null) {
            policy.setCoverageAmount(request.getCoverageAmount());
        }
        if (request.getDeductibleAmount() != null) {
            policy.setDeductibleAmount(request.getDeductibleAmount());
        }
        if (request.getPremiumAmount() != null) {
            policy.setPremiumAmount(request.getPremiumAmount());
        }
        if (request.getPremiumFrequency() != null) {
            policy.setPremiumFrequency(request.getPremiumFrequency());
        }
        if (request.getNetworkName() != null) {
            policy.setNetworkName(request.getNetworkName());
        }
        if (request.getStartDate() != null) {
            policy.setStartDate(request.getStartDate());
        }
        if (request.getExpiryDate() != null) {
            policy.setExpiryDate(request.getExpiryDate());
        }
        if (request.getDescription() != null) {
            policy.setDescription(request.getDescription());
        }

        // Validate date consistency after update
        if (policy.getExpiryDate().isBefore(policy.getStartDate())) {
            throw new IllegalArgumentException("Expiry date must be after start date");
        }

        Policy updated = policyRepository.save(policy);
        return toResponse(updated);
    }

    public PolicyResponse updatePolicyStatus(Long id, PolicyStatusUpdateRequest request) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException(id));
        policy.setStatus(request.getStatus());
        Policy updated = policyRepository.save(policy);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // DELETE / CANCEL
    // ──────────────────────────────────────────

    public void deletePolicy(Long id) {
        if (!policyRepository.existsById(id)) {
            throw new PolicyNotFoundException(id);
        }
        policyRepository.deleteById(id);
    }

    public PolicyResponse cancelPolicy(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new PolicyNotFoundException(id));
        policy.setStatus(PolicyStatus.CANCELLED);
        Policy updated = policyRepository.save(policy);
        return toResponse(updated);
    }

    // ──────────────────────────────────────────
    // PRIVATE HELPERS
    // ──────────────────────────────────────────

    private PolicyResponse toResponse(Policy policy) {
        return PolicyResponse.builder()
                .id(policy.getId())
                .policyNumber(policy.getPolicyNumber())
                .policyName(policy.getPolicyName())
                .category(policy.getCategory())
                .status(policy.getStatus())
                .customerId(policy.getCustomerId())
                .coverageAmount(policy.getCoverageAmount())
                .deductibleAmount(policy.getDeductibleAmount())
                .premiumAmount(policy.getPremiumAmount())
                .premiumFrequency(policy.getPremiumFrequency())
                .networkName(policy.getNetworkName())
                .startDate(policy.getStartDate())
                .expiryDate(policy.getExpiryDate())
                .description(policy.getDescription())
                .createdAt(policy.getCreatedAt())
                .updatedAt(policy.getUpdatedAt())
                .build();
    }

    private String generatePolicyNumber() {
        String yearMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String random = String.format("%05d", (int) (Math.random() * 100000));
        String candidate = "POL-" + yearMonth + "-" + random;
        // Retry on collision (statistically negligible but safe)
        while (policyRepository.existsByPolicyNumber(candidate)) {
            random = String.format("%05d", (int) (Math.random() * 100000));
            candidate = "POL-" + yearMonth + "-" + random;
        }
        return candidate;
    }
}
