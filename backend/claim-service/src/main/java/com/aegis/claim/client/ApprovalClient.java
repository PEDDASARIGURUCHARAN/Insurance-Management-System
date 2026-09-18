package com.aegis.claim.client;

import com.aegis.claim.dto.ApprovalClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "approval-service")
public interface ApprovalClient {

    @PostMapping("/api/approvals")
    ApprovalClientDto createApproval(@RequestBody ApprovalClientDto request);
}
