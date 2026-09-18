package com.aegis.policy.controller;

import com.aegis.policy.dto.PolicyRequest;
import com.aegis.policy.dto.PolicyResponse;
import com.aegis.policy.dto.PolicyStatusUpdateRequest;
import com.aegis.policy.dto.PolicyUpdateRequest;
import com.aegis.policy.enums.PolicyCategory;
import com.aegis.policy.enums.PolicyStatus;
import com.aegis.policy.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin(origins = "*")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // ──────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────

    /**
     * POST /api/policies
     * Create a new insurance policy.
     */
    @PostMapping
    public ResponseEntity<PolicyResponse> createPolicy(@Valid @RequestBody PolicyRequest request) {
        PolicyResponse response = policyService.createPolicy(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────────────────────────
    // READ
    // ──────────────────────────────────────────

    /**
     * GET /api/policies
     * Retrieve all policies. Optionally filter by status or category via query params.
     */
    @GetMapping
    public ResponseEntity<List<PolicyResponse>> getAllPolicies(
            @RequestParam(required = false) PolicyStatus status,
            @RequestParam(required = false) PolicyCategory category) {

        if (status != null) {
            return ResponseEntity.ok(policyService.getPoliciesByStatus(status));
        }
        if (category != null) {
            return ResponseEntity.ok(policyService.getPoliciesByCategory(category));
        }
        return ResponseEntity.ok(policyService.getAllPolicies());
    }

    /**
     * GET /api/policies/{id}
     * Retrieve a policy by its database ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PolicyResponse> getPolicyById(@PathVariable Long id) {
        return ResponseEntity.ok(policyService.getPolicyById(id));
    }

    /**
     * GET /api/policies/number/{policyNumber}
     * Retrieve a policy by its human-readable policy number (e.g. POL-202601-00042).
     */
    @GetMapping("/number/{policyNumber}")
    public ResponseEntity<PolicyResponse> getPolicyByPolicyNumber(@PathVariable String policyNumber) {
        return ResponseEntity.ok(policyService.getPolicyByPolicyNumber(policyNumber));
    }

    /**
     * GET /api/policies/customer/{customerId}
     * Retrieve all policies belonging to a specific customer.
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PolicyResponse>> getPoliciesByCustomer(
            @PathVariable Long customerId,
            @RequestParam(required = false) PolicyStatus status) {

        if (status != null) {
            return ResponseEntity.ok(policyService.getPoliciesByCustomerAndStatus(customerId, status));
        }
        return ResponseEntity.ok(policyService.getPoliciesByCustomer(customerId));
    }

    /**
     * GET /api/policies/status/{status}
     * Retrieve all policies with a specific status.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<PolicyResponse>> getPoliciesByStatus(@PathVariable PolicyStatus status) {
        return ResponseEntity.ok(policyService.getPoliciesByStatus(status));
    }

    /**
     * GET /api/policies/category/{category}
     * Retrieve all policies of a specific category.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<PolicyResponse>> getPoliciesByCategory(@PathVariable PolicyCategory category) {
        return ResponseEntity.ok(policyService.getPoliciesByCategory(category));
    }

    // ──────────────────────────────────────────
    // UPDATE
    // ──────────────────────────────────────────

    /**
     * PUT /api/policies/{id}
     * Full or partial update of a policy. Only non-null fields are applied.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PolicyResponse> updatePolicy(
            @PathVariable Long id,
            @Valid @RequestBody PolicyUpdateRequest request) {
        return ResponseEntity.ok(policyService.updatePolicy(id, request));
    }

    /**
     * PATCH /api/policies/{id}/status
     * Update only the status of a policy (activate, cancel, suspend, etc.).
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<PolicyResponse> updatePolicyStatus(
            @PathVariable Long id,
            @Valid @RequestBody PolicyStatusUpdateRequest request) {
        return ResponseEntity.ok(policyService.updatePolicyStatus(id, request));
    }

    /**
     * PATCH /api/policies/{id}/cancel
     * Convenience endpoint to cancel a policy (sets status to CANCELLED).
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<PolicyResponse> cancelPolicy(@PathVariable Long id) {
        return ResponseEntity.ok(policyService.cancelPolicy(id));
    }

    // ──────────────────────────────────────────
    // DELETE
    // ──────────────────────────────────────────

    /**
     * DELETE /api/policies/{id}
     * Permanently remove a policy record.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.noContent().build();
    }
}
