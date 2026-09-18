package com.aegis.claim.controller;

import com.aegis.claim.dto.*;
import com.aegis.claim.enums.ClaimCategory;
import com.aegis.claim.enums.ClaimPriority;
import com.aegis.claim.enums.ClaimStatus;
import com.aegis.claim.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "*")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    // ──────────────────────────────────────────
    // CREATE
    // ──────────────────────────────────────────

    /**
     * POST /api/claims
     * Submit a new insurance claim.
     */
    @PostMapping
    public ResponseEntity<ClaimResponse> submitClaim(@Valid @RequestBody ClaimRequest request) {
        ClaimResponse response = claimService.submitClaim(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ──────────────────────────────────────────
    // READ
    // ──────────────────────────────────────────

    /**
     * GET /api/claims
     * Retrieve all claims. Optionally filter via query params: status, category, priority.
     */
    @GetMapping
    public ResponseEntity<List<ClaimResponse>> getAllClaims(
            @RequestParam(required = false) ClaimStatus status,
            @RequestParam(required = false) ClaimCategory category,
            @RequestParam(required = false) ClaimPriority priority) {

        if (status != null) {
            return ResponseEntity.ok(claimService.getClaimsByStatus(status));
        }
        if (category != null) {
            return ResponseEntity.ok(claimService.getClaimsByCategory(category));
        }
        if (priority != null) {
            return ResponseEntity.ok(claimService.getClaimsByPriority(priority));
        }
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    /**
     * GET /api/claims/{id}
     * Retrieve a claim by its database ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClaimResponse> getClaimById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    /**
     * GET /api/claims/number/{claimNumber}
     * Retrieve a claim by its human-readable number (e.g. CLM-202609-00042).
     */
    @GetMapping("/number/{claimNumber}")
    public ResponseEntity<ClaimResponse> getClaimByClaimNumber(@PathVariable String claimNumber) {
        return ResponseEntity.ok(claimService.getClaimByClaimNumber(claimNumber));
    }

    /**
     * GET /api/claims/customer/{customerId}
     * Retrieve all claims for a specific customer (most recent first).
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByCustomer(
            @PathVariable Long customerId,
            @RequestParam(required = false) ClaimStatus status) {

        if (status != null) {
            return ResponseEntity.ok(claimService.getClaimsByCustomerAndStatus(customerId, status));
        }
        return ResponseEntity.ok(claimService.getClaimsByCustomer(customerId));
    }

    /**
     * GET /api/claims/policy/{policyId}
     * Retrieve all claims filed against a specific policy.
     */
    @GetMapping("/policy/{policyId}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByPolicy(
            @PathVariable Long policyId,
            @RequestParam(required = false) ClaimStatus status) {

        if (status != null) {
            return ResponseEntity.ok(claimService.getClaimsByPolicyAndStatus(policyId, status));
        }
        return ResponseEntity.ok(claimService.getClaimsByPolicy(policyId));
    }

    /**
     * GET /api/claims/status/{status}
     * Retrieve all claims with a given status (path variable).
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByStatus(@PathVariable ClaimStatus status) {
        return ResponseEntity.ok(claimService.getClaimsByStatus(status));
    }

    /**
     * GET /api/claims/category/{category}
     * Retrieve all claims of a given category.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByCategory(@PathVariable ClaimCategory category) {
        return ResponseEntity.ok(claimService.getClaimsByCategory(category));
    }

    /**
     * GET /api/claims/priority/{priority}
     * Retrieve all claims of a given priority.
     */
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<ClaimResponse>> getClaimsByPriority(@PathVariable ClaimPriority priority) {
        return ResponseEntity.ok(claimService.getClaimsByPriority(priority));
    }

    /**
     * GET /api/claims/{id}/status/stream
     * Server-Sent Events (SSE) endpoint to stream real-time claim status updates.
     */
    @GetMapping("/{id}/status/stream")
    public org.springframework.web.servlet.mvc.method.annotation.SseEmitter streamClaimStatus(@PathVariable Long id) {
        org.springframework.web.servlet.mvc.method.annotation.SseEmitter emitter = new org.springframework.web.servlet.mvc.method.annotation.SseEmitter(600000L); // 10 minutes timeout
        
        java.util.concurrent.ExecutorService sseMvcExecutor = java.util.concurrent.Executors.newSingleThreadExecutor();
        sseMvcExecutor.execute(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    ClaimResponse response = claimService.getClaimById(id);
                    org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder event = org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event()
                            .data(response.getStatus().name())
                            .id(String.valueOf(System.currentTimeMillis()))
                            .name("statusUpdate");
                    emitter.send(event);
                    Thread.sleep(5000); // Send status every 5 seconds (simulating real-time polling/push)
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        
        return emitter;
    }

    // ──────────────────────────────────────────
    // UPDATE
    // ──────────────────────────────────────────

    /**
     * PUT /api/claims/{id}
     * General update of a claim. Only non-null fields are applied.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClaimResponse> updateClaim(
            @PathVariable Long id,
            @Valid @RequestBody ClaimUpdateRequest request) {
        return ResponseEntity.ok(claimService.updateClaim(id, request));
    }

    /**
     * PATCH /api/claims/{id}/status
     * Update only the status (and optional stage / rejection reason) of a claim.
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<ClaimResponse> updateClaimStatus(
            @PathVariable Long id,
            @Valid @RequestBody ClaimStatusUpdateRequest request) {
        return ResponseEntity.ok(claimService.updateClaimStatus(id, request));
    }

    /**
     * PATCH /api/claims/{id}/adjudicate
     * Approve, reject, or settle a claim with optional approved amount and resolution date.
     */
    @PatchMapping("/{id}/adjudicate")
    public ResponseEntity<ClaimResponse> adjudicateClaim(
            @PathVariable Long id,
            @Valid @RequestBody ClaimApprovalRequest request) {
        return ResponseEntity.ok(claimService.adjudicateClaim(id, request));
    }

    /**
     * PATCH /api/claims/{id}/cancel
     * Convenience endpoint to cancel a claim.
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ClaimResponse> cancelClaim(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.cancelClaim(id));
    }

    // ──────────────────────────────────────────
    // DELETE
    // ──────────────────────────────────────────

    /**
     * DELETE /api/claims/{id}
     * Permanently remove a claim record.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable Long id) {
        claimService.deleteClaim(id);
        return ResponseEntity.noContent().build();
    }
}
