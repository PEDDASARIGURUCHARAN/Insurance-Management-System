package com.aegis.policy.repository;

import com.aegis.policy.entity.Policy;
import com.aegis.policy.enums.PolicyCategory;
import com.aegis.policy.enums.PolicyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findByCustomerId(Long customerId);

    List<Policy> findByStatus(PolicyStatus status);

    List<Policy> findByCategory(PolicyCategory category);

    List<Policy> findByCustomerIdAndStatus(Long customerId, PolicyStatus status);

    Optional<Policy> findByPolicyNumber(String policyNumber);

    boolean existsByPolicyNumber(String policyNumber);
}
