package com.aegis.approval.controller;

import com.aegis.approval.dto.*;
import com.aegis.approval.enums.ApprovalPriority;
import com.aegis.approval.enums.ApprovalStatus;
import com.aegis.approval.enums.ApprovalType;
import com.aegis.approval.service.ApprovalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
@CrossOrigin(origins = "*")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    // ──────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────

    /**
     * POST /api/approvals
     * Submit a new approval request.
     */
    @PostMapping
    public ResponseEntity<ApprovalResponse> createApproval(@Valid @RequestBody ApprovalRequest request) {
        ApprovalResponse response = approvalService.createApproval(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────────────────────────
    // READ
    // ──────────────────────────────────────────

    /**
     * GET /api/approvals
     * Get all approvals. Optional query params: status, type, priority.
     */
    @GetMapping
    public ResponseEntity<List<ApprovalResponse>> getAllApprovals(
            @RequestParam(required = false) ApprovalStatus status,
            @RequestParam(required = false) ApprovalType type,
            @RequestParam(required = false) ApprovalPriority priority) {

        if (status != null) {
            return ResponseEntity.ok(approvalService.getApprovalsByStatus(status));
        }
        if (type != null) {
            return ResponseEntity.ok(approvalService.getApprovalsByType(type));
        }
        if (priority != null) {
            return ResponseEntity.ok(approvalService.getApprovalsByPriority(priority));
        }
        return ResponseEntity.ok(approvalService.getAllApprovals());
    }

    /**
     * GET /api/approvals/{id}
     * Get an approval by database ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApprovalResponse> getApprovalById(@PathVariable Long id) {
        return ResponseEntity.ok(approvalService.getApprovalById(id));
    }

    /**
     * GET /api/approvals/number/{approvalNumber}
     * Get an approval by its human-readable number (e.g. APR-202609-00042).
     */
    @GetMapping("/number/{approvalNumber}")
    public ResponseEntity<ApprovalResponse> getApprovalByNumber(@PathVariable String approvalNumber) {
        return ResponseEntity.ok(approvalService.getApprovalByNumber(approvalNumber));
    }

    /**
     * GET /api/approvals/customer/{customerId}
     * Get all approvals for a specific customer (most recent first).
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ApprovalResponse>> getApprovalsByCustomer(
            @PathVariable Long customerId,
            @RequestParam(required = false) ApprovalStatus status) {

        if (status != null) {
            return ResponseEntity.ok(approvalService.getApprovalsByCustomerAndStatus(customerId, status));
        }
        return ResponseEntity.ok(approvalService.getApprovalsByCustomer(customerId));
    }

    /**
     * GET /api/approvals/claim/{claimId}
     * Get all approvals linked to a specific claim.
     */
    @GetMapping("/claim/{claimId}")
    public ResponseEntity<List<ApprovalResponse>> getApprovalsByClaim(@PathVariable Long claimId) {
        return ResponseEntity.ok(approvalService.getApprovalsByClaim(claimId));
    }

    /**
     * GET /api/approvals/policy/{policyId}
     * Get all approvals linked to a specific policy.
     */
    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<ApprovalResponse>> getApprovalsByPolicy(@PathVariable Long policyId) {
        return ResponseEntity.ok(approvalService.getApprovalsByPolicy(policyId));
    }

    /**
     * GET /api/approvals/status/{status}
     * Get all approvals of a specific status (path variable, most recent first).
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApprovalResponse>> getApprovalsByStatus(@PathVariable ApprovalStatus status) {
        return ResponseEntity.ok(approvalService.getApprovalsByStatus(status));
    }

    /**
     * GET /api/approvals/type/{type}
     * Get all approvals of a specific type.
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<ApprovalResponse>> getApprovalsByType(@PathVariable ApprovalType type) {
        return ResponseEntity.ok(approvalService.getApprovalsByType(type));
    }

    /**
     * GET /api/approvals/priority/{priority}
     * Get all approvals of a specific priority.
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<ApprovalResponse>> getApprovalsByPriority(@PathVariable ApprovalPriority priority) {
        return ResponseEntity.ok(approvalService.getApprovalsByPriority(priority));
    }

    /**
     * GET /api/approvals/reviewer/{reviewerId}
     * Get all approvals assigned to a specific reviewer.
     */
    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<List<ApprovalResponse>> getApprovalsByReviewer(@PathVariable Long reviewerId) {
        return ResponseEntity.ok(approvalService.getApprovalsByReviewer(reviewerId));
    }

    // ──────────────────────────────────────────
    // UPDATE
    // ──────────────────────────────────────────

    /**
     * PUT /api/approvals/{id}
     * General update — only non-null fields are applied.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApprovalResponse> updateApproval(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalUpdateRequest request) {
        return ResponseEntity.ok(approvalService.updateApproval(id, request));
    }

    /**
     * PATCH /api/approvals/{id}/decide
     * Make a formal approval/rejection decision. Validates terminal-state guard.
     */
    @PatchMapping("/{id}/decide")
    public ResponseEntity<ApprovalResponse> makeDecision(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalDecisionRequest request) {
        return ResponseEntity.ok(approvalService.makeDecision(id, request));
    }

    /**
     * PATCH /api/approvals/{id}/remarks
     * Add reviewer remarks. Auto-advances PENDING → UNDER_REVIEW.
     */
    @PatchMapping("/{id}/remarks")
    public ResponseEntity<ApprovalResponse> addRemarks(
            @PathVariable Long id,
            @Valid @RequestBody ApprovalRemarksRequest request) {
        return ResponseEntity.ok(approvalService.addRemarks(id, request));
    }

    /**
     * PATCH /api/approvals/{id}/withdraw
     * Withdraw a pending or under-review approval request.
     */
    @PatchMapping("/{id}/withdraw")
    public ResponseEntity<ApprovalResponse> withdrawApproval(@PathVariable Long id) {
        return ResponseEntity.ok(approvalService.withdrawApproval(id));
    }

    // ──────────────────────────────────────────
    // DELETE
    // ──────────────────────────────────────────

    /**
     * DELETE /api/approvals/{id}
     * Permanently delete an approval record.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        approvalService.deleteApproval(id);
        return ResponseEntity.noContent().build();
    }
}
