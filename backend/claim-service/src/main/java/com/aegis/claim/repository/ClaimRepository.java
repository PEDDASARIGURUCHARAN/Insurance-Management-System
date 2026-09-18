package com.aegis.claim.repository;

import com.aegis.claim.entity.Claim;
import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import com.aegis.claim.enums.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {

    Optional<Claim> findByClaimNumber(String claimNumber);

    boolean existsByClaimNumber(String claimNumber);

    List<Claim> findByCustomerId(Long customerId);

    List<Claim> findByPolicyId(Long policyId);

    List<Claim> findByStatus(ClaimStatus status);

    List<Claim> findByCategory(ClaimCategory category);

    List<Claim> findByPriority(ClaimPriority priority);

    List<Claim> findByCustomerIdAndStatus(Long customerId, ClaimStatus status);

    List<Claim> findByPolicyIdAndStatus(Long policyId, ClaimStatus status);

    List<Claim> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
