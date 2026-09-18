package com.aegis.claim.client;

import com.aegis.claim.dto.PolicyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "policy-service")
public interface PolicyClient {

    @GetMapping("/api/policies/{id}")
    PolicyDto getPolicyById(@PathVariable("id") Long id);

    @GetMapping("/api/policies/number/{policyNumber}")
    PolicyDto getPolicyByNumber(@PathVariable("policyNumber") String policyNumber);
}
