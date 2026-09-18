package com.aegis.claim.service;

import com.aegis.claim.client.ApprovalClient;
import com.aegis.claim.client.PolicyClient;
import com.aegis.claim.dto.*;
import com.aegis.claim.entity.Claim;
import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import com.aegis.claim.enums.ClaimStatus;
import com.aegis.claim.exception.ClaimNotFoundException;
import com.aegis.claim.repository.ClaimRepository;
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
public class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private PolicyClient policyClient;

    @Mock
    private ApprovalClient approvalClient;

    @InjectMocks
    private ClaimService claimService;

    private Claim sampleClaim;
    private ClaimRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleClaim = Claim.builder()
                .id(1L)
                .claimNumber("CLM-202609-54321")
                .customerId(101L)
                .policyId(1L)
                .policyNumber("POL-202609-12345")
                .category(ClaimCategory.MEDICAL)
                .priority(ClaimPriority.NORMAL)
                .status(ClaimStatus.SUBMITTED)
                .claimedAmount(new BigDecimal("1500.00"))
                .description("Emergency medical treatment")
                .incidentDate(LocalDate.now().minusDays(2))
                .submissionDate(LocalDate.now())
                .stage("Initial Review")
                .build();

        sampleRequest = new ClaimRequest();
        sampleRequest.setCustomerId(101L);
        sampleRequest.setPolicyId(1L);
        sampleRequest.setPolicyNumber("POL-202609-12345");
        sampleRequest.setCategory(ClaimCategory.MEDICAL);
        sampleRequest.setPriority(ClaimPriority.NORMAL);
        sampleRequest.setClaimedAmount(new BigDecimal("1500.00"));
        sampleRequest.setDescription("Emergency medical treatment");
        sampleRequest.setIncidentDate(LocalDate.now().minusDays(2));
    }

    @Test
    void submitClaim_Success() {
        when(claimRepository.existsByClaimNumber(any())).thenReturn(false);
        when(claimRepository.save(any(Claim.class))).thenReturn(sampleClaim);

        PolicyDto mockPolicy = new PolicyDto();
        mockPolicy.setId(1L);
        mockPolicy.setPolicyNumber("POL-202609-12345");
        when(policyClient.getPolicyById(1L)).thenReturn(mockPolicy);

        ClaimResponse response = claimService.submitClaim(sampleRequest);

        assertNotNull(response);
        assertEquals("CLM-202609-54321", response.getClaimNumber());
        assertEquals(ClaimStatus.SUBMITTED, response.getStatus());
        verify(claimRepository).save(any(Claim.class));
        verify(approvalClient).createApproval(any(ApprovalClientDto.class));
    }

    @Test
    void getClaimById_Success() {
        when(claimRepository.findById(1L)).thenReturn(Optional.of(sampleClaim));

        ClaimResponse response = claimService.getClaimById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getClaimById_NotFound() {
        when(claimRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ClaimNotFoundException.class, () -> claimService.getClaimById(99L));
    }

    @Test
    void adjudicateClaim_ApproveSuccess() {
        when(claimRepository.findById(1L)).thenReturn(Optional.of(sampleClaim));
        when(claimRepository.save(any(Claim.class))).thenReturn(sampleClaim);

        ClaimApprovalRequest approvalReq = new ClaimApprovalRequest();
        approvalReq.setStatus(ClaimStatus.APPROVED);
        approvalReq.setApprovedAmount(new BigDecimal("1500.00"));

        ClaimResponse response = claimService.adjudicateClaim(1L, approvalReq);

        assertNotNull(response);
        verify(claimRepository).save(any(Claim.class));
    }
}
