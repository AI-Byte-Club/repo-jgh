package com.example.feat005.controller;

import com.example.feat005.service.ExternalApiService;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExternalApiController {

    private final ExternalApiService externalApiService;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @GetMapping("/test/success")
    public String testSuccess() {
        return externalApiService.callSuccess();
    }

    @GetMapping("/test/timeout")
    public String testTimeout() {
        return externalApiService.callSlow();
    }

    @GetMapping("/test/retry")
    public String testRetry() {
        return externalApiService.callError();
    }

    @GetMapping("/test/bulkhead")
    public String testBulkhead() {
        return externalApiService.callBulkhead();
    }

    @GetMapping("/test/circuit-breaker")
    public String testCircuitBreaker() {
        return externalApiService.callError();
    }

    @GetMapping("/test/circuit-status")
    public String testCircuitStatus() {
        return circuitBreakerRegistry.circuitBreaker("externalCircuitBreaker").getState().toString();
    }
}
