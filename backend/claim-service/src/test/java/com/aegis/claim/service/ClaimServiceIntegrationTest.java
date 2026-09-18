package com.aegis.claim.service;

import com.aegis.claim.dto.ClaimRequest;
import com.aegis.claim.dto.ClaimResponse;
import com.aegis.claim.entity.Claim;
import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import com.aegis.claim.repository.ClaimRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.aegis.claim.client.PolicyClient;
import com.aegis.claim.client.ApprovalClient;
import com.aegis.claim.dto.PolicyDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ClaimServiceIntegrationTest {

    @Autowired
    private ClaimService claimService;

    @Autowired
    private ClaimRepository claimRepository;

    @MockBean
    private PolicyClient policyClient;

    @MockBean
    private ApprovalClient approvalClient;

    @Test
    public void testSubmitClaimAndStoreInDatabase() {
        // Setup mock for external Feign clients
        PolicyDto mockPolicy = new PolicyDto();
        mockPolicy.setId(1L);
        mockPolicy.setPolicyNumber("POL-12345");
        when(policyClient.getPolicyById(anyLong())).thenReturn(mockPolicy);

        // Prepare request
        ClaimRequest request = new ClaimRequest();
        request.setCustomerId(101L);
        request.setPolicyId(1L);
        request.setPolicyNumber("POL-12345");
        request.setCategory(ClaimCategory.MEDICAL);
        request.setPriority(ClaimPriority.NORMAL);
        request.setClaimedAmount(new BigDecimal("1500.00"));
        request.setDescription("Emergency treatment");
        request.setIncidentDate(LocalDate.now().minusDays(2));

        // Execute integration
        ClaimResponse response = claimService.submitClaim(request);

        // Verify result and database state
        assertNotNull(response.getId());
        assertNotNull(response.getClaimNumber());

        Optional<Claim> savedClaim = claimRepository.findById(response.getId());
        assertEquals(true, savedClaim.isPresent());
        assertEquals(new BigDecimal("1500.00"), savedClaim.get().getClaimedAmount());
    }
}
