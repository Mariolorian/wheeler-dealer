package com.mariolorian.wheelerdealer.service;

import com.mariolorian.wheelerdealer.api.NbpFeignClient;
import com.mariolorian.wheelerdealer.exception.NbpExchangeServiceOfflineException;
import com.mariolorian.wheelerdealer.model.dto.CurrencyDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NpbCurrencyService implements CurrencyService {

    private final NbpFeignClient feignClient;

    @Override
    public CurrencyDto receiveCurrentCurrencyStatus() {
        try {
            return feignClient.receiveCurrentCurrencyStatus();
        } catch (FeignException exception) {
            throw new NbpExchangeServiceOfflineException();
        }
    }

}
