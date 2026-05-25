package com.example.feat005.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalApiService {

    private final RestTemplate restTemplate;
    private final String BASE_URL = "http://localhost:8089/api/mock";

    @Retry(name = "externalRetry")
    @CircuitBreaker(name = "externalCircuitBreaker", fallbackMethod = "fallback")
    public String callSuccess() {
        return restTemplate.getForObject(BASE_URL + "/success", String.class);
    }

    @Retry(name = "externalRetry")
    @CircuitBreaker(name = "externalCircuitBreaker", fallbackMethod = "fallback")
    public String callSlow() {
        return restTemplate.getForObject(BASE_URL + "/slow", String.class);
    }

    @Retry(name = "externalRetry")
    @CircuitBreaker(name = "externalCircuitBreaker", fallbackMethod = "fallback")
    public String callError() {
        log.info("Attempting to call error API...");
        return restTemplate.getForObject(BASE_URL + "/error", String.class);
    }

    @Bulkhead(name = "externalBulkhead", fallbackMethod = "fallback")
    public String callBulkhead() {
        return restTemplate.getForObject(BASE_URL + "/success", String.class);
    }

    public String fallback(Throwable t) {
        log.error("Fallback triggered due to: {}", t.getMessage());
        return "서비스 점검 중입니다";
    }
}
