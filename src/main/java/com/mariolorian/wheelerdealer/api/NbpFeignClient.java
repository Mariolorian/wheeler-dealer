package com.mariolorian.wheelerdealer.api;

import com.mariolorian.wheelerdealer.api.config.NbpFeignConfig;
import com.mariolorian.wheelerdealer.model.dto.CurrencyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "npb", url = "${exchange-service.nbp.usd}", configuration = NbpFeignConfig.class)
public interface NbpFeignClient {

    @GetMapping
    CurrencyDto receiveCurrentCurrencyStatus();

}
