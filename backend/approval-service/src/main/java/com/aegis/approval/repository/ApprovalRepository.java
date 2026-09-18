package com.aegis.approval.repository;

import com.aegis.approval.entity.Approval;
import com.aegis.approval.enums.ApprovalPriority;
import com.aegis.approval.enums.ApprovalStatus;
import com.aegis.approval.enums.ApprovalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    Optional<Approval> findByApprovalNumber(String approvalNumber);

    boolean existsByApprovalNumber(String approvalNumber);

    List<Approval> findByCustomerId(Long customerId);

    List<Approval> findByClaimId(Long claimId);

    List<Approval> findByPolicyId(Long policyId);

    List<Approval> findByStatus(ApprovalStatus status);

    List<Approval> findByApprovalType(ApprovalType approvalType);

    List<Approval> findByPriority(ApprovalPriority priority);

    List<Approval> findByReviewerId(Long reviewerId);

    List<Approval> findByCustomerIdAndStatus(Long customerId, ApprovalStatus status);

    List<Approval> findByStatusOrderByCreatedAtDesc(ApprovalStatus status);

    List<Approval> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
