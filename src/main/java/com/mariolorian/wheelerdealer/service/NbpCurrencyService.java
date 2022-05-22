package com.mariolorian.wheelerdealer.service;

import com.mariolorian.wheelerdealer.api.NbpFeignClient;
import com.mariolorian.wheelerdealer.model.dto.CurrencyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NbpCurrencyService implements CurrencyService {

    private final NbpFeignClient feignClient;

    @Override
    public CurrencyDto receiveCurrentCurrencyStatus() {
        log.info("Connecting to NPB exchange service...");
        return feignClient.receiveCurrentCurrencyStatus();
    }

}
