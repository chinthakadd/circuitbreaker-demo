package com.chinthakad.poc.circuitbreakerdemo.controller;

import com.chinthakad.poc.circuitbreakerdemo.client.RestClient;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consume")
public class ConsumerController {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private RestClient restClient;

    @GetMapping
    public String getMapping() {
        return this.circuitBreakerFactory.create("circuit-breaker-01")
                .run(() -> retry(),
                        throwable -> restClient.consumeAlternative());
    }

    public String retry() {
        try {
            return Retry.of(
                    "retry-01",
                    RetryConfig.custom()
                            .maxAttempts(5)
                            .build()

            )
                    .executeCallable(
                            () -> restClient.consume()
                    );
        } catch (Exception e) {
            throw new RuntimeException("Retries Failed Too");
        }
    }
}
