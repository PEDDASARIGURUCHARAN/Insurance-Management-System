package com.aegis.policy.service;

import com.aegis.policy.dto.PolicyRequest;
import com.aegis.policy.dto.PolicyResponse;
import com.aegis.policy.dto.PolicyStatusUpdateRequest;
import com.aegis.policy.entity.Policy;
import com.aegis.policy.enums.PolicyCategory;
import com.aegis.policy.enums.PolicyStatus;
import com.aegis.policy.exception.PolicyNotFoundException;
import com.aegis.policy.repository.PolicyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PolicyServiceTest {

    @Mock
    private PolicyRepository policyRepository;

    @InjectMocks
    private PolicyService policyService;

    private Policy samplePolicy;
    private PolicyRequest sampleRequest;

    @BeforeEach
    void setUp() {
        samplePolicy = Policy.builder()
                .id(1L)
                .policyNumber("POL-202609-12345")
                .policyName("Comprehensive Health Guard")
                .category(PolicyCategory.HEALTH)
                .status(PolicyStatus.ACTIVE)
                .customerId(101L)
                .coverageAmount(new BigDecimal("50000.00"))
                .deductibleAmount(new BigDecimal("500.00"))
                .premiumAmount(new BigDecimal("250.00"))
                .startDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusYears(1))
                .build();

        sampleRequest = new PolicyRequest();
        sampleRequest.setPolicyName("Comprehensive Health Guard");
        sampleRequest.setCategory(PolicyCategory.HEALTH);
        sampleRequest.setCustomerId(101L);
        sampleRequest.setCoverageAmount(new BigDecimal("50000.00"));
        sampleRequest.setDeductibleAmount(new BigDecimal("500.00"));
        sampleRequest.setPremiumAmount(new BigDecimal("250.00"));
        sampleRequest.setStartDate(LocalDate.now());
        sampleRequest.setExpiryDate(LocalDate.now().plusYears(1));
    }

    @Test
    void createPolicy_Success() {
        when(policyRepository.existsByPolicyNumber(any())).thenReturn(false);
        when(policyRepository.save(any(Policy.class))).thenReturn(samplePolicy);

        PolicyResponse response = policyService.createPolicy(sampleRequest);

        assertNotNull(response);
        assertEquals("Comprehensive Health Guard", response.getPolicyName());
        assertEquals(PolicyStatus.ACTIVE, response.getStatus());
        verify(policyRepository, times(1)).save(any(Policy.class));
    }

    @Test
    void getPolicyById_Success() {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(samplePolicy));

        PolicyResponse response = policyService.getPolicyById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("POL-202609-12345", response.getPolicyNumber());
    }

    @Test
    void getPolicyById_NotFound() {
        when(policyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PolicyNotFoundException.class, () -> policyService.getPolicyById(99L));
    }

    @Test
    void getPoliciesByCustomer_Success() {
        when(policyRepository.findByCustomerId(101L)).thenReturn(List.of(samplePolicy));

        List<PolicyResponse> responses = policyService.getPoliciesByCustomer(101L);

        assertEquals(1, responses.size());
        assertEquals(101L, responses.get(0).getCustomerId());
    }

    @Test
    void updatePolicyStatus_Success() {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(samplePolicy));
        when(policyRepository.save(any(Policy.class))).thenReturn(samplePolicy);

        PolicyStatusUpdateRequest updateReq = new PolicyStatusUpdateRequest();
        updateReq.setStatus(PolicyStatus.SUSPENDED);

        PolicyResponse response = policyService.updatePolicyStatus(1L, updateReq);

        assertNotNull(response);
        verify(policyRepository).save(any(Policy.class));
    }

    @Test
    void cancelPolicy_Success() {
        when(policyRepository.findById(1L)).thenReturn(Optional.of(samplePolicy));
        when(policyRepository.save(any(Policy.class))).thenReturn(samplePolicy);

        PolicyResponse response = policyService.cancelPolicy(1L);

        assertNotNull(response);
        verify(policyRepository).save(any(Policy.class));
    }
}
