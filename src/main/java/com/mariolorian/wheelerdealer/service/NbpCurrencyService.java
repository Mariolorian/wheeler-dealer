package com.mariolorian.wheelerdealer.service;

import com.mariolorian.wheelerdealer.api.NbpFeignClient;
import com.mariolorian.wheelerdealer.exception.NbpExchangeServiceOfflineException;
import com.mariolorian.wheelerdealer.model.dto.CurrencyDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NbpCurrencyService implements CurrencyService {

    private final NbpFeignClient feignClient;

    @Override
    public CurrencyDto receiveCurrentCurrencyStatus() {
        try {
            log.info("Connecting to NPB exchange service...");
            return feignClient.receiveCurrentCurrencyStatus();
        } catch (FeignException exception) {
            log.error("Unable to connect with NBP exchange service");
            throw new NbpExchangeServiceOfflineException();
        }
    }

}
