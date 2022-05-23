package com.mariolorian.wheelerdealer.service;

import com.mariolorian.wheelerdealer.exception.NbpExchangeServiceOfflineException;
import com.mariolorian.wheelerdealer.model.dto.CurrencyDto;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NbpCurrencyResilientService implements CurrencyService {

    private final CurrencyService currencyService;
    private final CircuitBreaker circuitBreaker;
    private final Retry retry;

    public CurrencyDto receiveCurrentCurrencyStatus() {
        return Decorators.ofSupplier(currencyService::receiveCurrentCurrencyStatus)
                .withCircuitBreaker(circuitBreaker)
                .withRetry(retry)
                .withFallback(fallback -> {
                    throw new NbpExchangeServiceOfflineException();
                })
                .get();
    }
}
