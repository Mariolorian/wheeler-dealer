package com.mariolorian.wheelerdealer.api.config;

import com.mariolorian.wheelerdealer.service.CurrencyService;
import com.mariolorian.wheelerdealer.service.NbpCurrencyResilientService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class ResilientExchangeServiceConfig {

    @Bean
    public NbpCurrencyResilientService nbpCurrencyResilientService(CurrencyService currencyService) {
        return new NbpCurrencyResilientService(currencyService, circuitBreaker(), retry());
    }

    private CircuitBreaker circuitBreaker() {
        CircuitBreaker circuitBreaker = CircuitBreaker.of("Exchange founds", CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .minimumNumberOfCalls(3)
                .slidingWindowSize(3)
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .waitDurationInOpenState(Duration.ofSeconds(5))
                .permittedNumberOfCallsInHalfOpenState(1)
                .build());
        circuitBreaker.getEventPublisher()
                .onEvent(event -> log.info("Exchange founds event: {}", event));
        return circuitBreaker;
    }

    private Retry retry() {
        Retry retry = Retry.of("Exchange founds", RetryConfig.custom()
                .maxAttempts(10)
                .waitDuration(Duration.ofMillis(2000))
                .build());
        retry.getEventPublisher()
                .onEvent(event -> log.info("Exchange funds event: {}", event));
        return retry;
    }



}
