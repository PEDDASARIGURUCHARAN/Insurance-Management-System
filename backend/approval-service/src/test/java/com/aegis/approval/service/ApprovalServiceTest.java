package com.aegis.approval.service;

import com.aegis.approval.dto.*;
import com.aegis.approval.entity.Approval;
import com.aegis.approval.enums.ApprovalPriority;
import com.aegis.approval.enums.ApprovalStatus;
import com.aegis.approval.enums.ApprovalType;
import com.aegis.approval.exception.ApprovalNotFoundException;
import com.aegis.approval.repository.ApprovalRepository;
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
public class ApprovalServiceTest {

    @Mock
    private ApprovalRepository approvalRepository;

    @InjectMocks
    private ApprovalService approvalService;

    private Approval sampleApproval;
    private ApprovalRequest sampleRequest;

    @BeforeEach
    void setUp() {
        sampleApproval = Approval.builder()
                .id(1L)
                .approvalNumber("APR-202609-99999")
                .approvalType(ApprovalType.CLAIM_SETTLEMENT)
                .status(ApprovalStatus.PENDING)
                .priority(ApprovalPriority.NORMAL)
                .customerId(101L)
                .claimId(10L)
                .claimNumber("CLM-202609-54321")
                .policyId(1L)
                .policyNumber("POL-202609-12345")
                .requestedAmount(new BigDecimal("1500.00"))
                .subject("Claim Settlement Request for CLM-202609-54321")
                .description("Automated approval request created")
                .requestedDate(LocalDate.now())
                .build();

        sampleRequest = new ApprovalRequest();
        sampleRequest.setApprovalType(ApprovalType.CLAIM_SETTLEMENT);
        sampleRequest.setCustomerId(101L);
        sampleRequest.setClaimId(10L);
        sampleRequest.setClaimNumber("CLM-202609-54321");
        sampleRequest.setPolicyId(1L);
        sampleRequest.setPolicyNumber("POL-202609-12345");
        sampleRequest.setRequestedAmount(new BigDecimal("1500.00"));
        sampleRequest.setSubject("Claim Settlement Request for CLM-202609-54321");
    }

    @Test
    void createApproval_Success() {
        when(approvalRepository.existsByApprovalNumber(any())).thenReturn(false);
        when(approvalRepository.save(any(Approval.class))).thenReturn(sampleApproval);

        ApprovalResponse response = approvalService.createApproval(sampleRequest);

        assertNotNull(response);
        assertEquals(ApprovalStatus.PENDING, response.getStatus());
        assertEquals("APR-202609-99999", response.getApprovalNumber());
        verify(approvalRepository).save(any(Approval.class));
    }

    @Test
    void getApprovalById_Success() {
        when(approvalRepository.findById(1L)).thenReturn(Optional.of(sampleApproval));

        ApprovalResponse response = approvalService.getApprovalById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void getApprovalById_NotFound() {
        when(approvalRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ApprovalNotFoundException.class, () -> approvalService.getApprovalById(99L));
    }

    @Test
    void makeDecision_ApproveSuccess() {
        when(approvalRepository.findById(1L)).thenReturn(Optional.of(sampleApproval));
        when(approvalRepository.save(any(Approval.class))).thenReturn(sampleApproval);

        ApprovalDecisionRequest decisionReq = new ApprovalDecisionRequest();
        decisionReq.setStatus(ApprovalStatus.APPROVED);
        decisionReq.setApprovedAmount(new BigDecimal("1500.00"));
        decisionReq.setRemarks("Verified and approved.");

        ApprovalResponse response = approvalService.makeDecision(1L, decisionReq);

        assertNotNull(response);
        verify(approvalRepository).save(any(Approval.class));
    }

    @Test
    void withdrawApproval_Success() {
        when(approvalRepository.findById(1L)).thenReturn(Optional.of(sampleApproval));
        when(approvalRepository.save(any(Approval.class))).thenReturn(sampleApproval);

        ApprovalResponse response = approvalService.withdrawApproval(1L);

        assertNotNull(response);
        verify(approvalRepository).save(any(Approval.class));
    }
}
