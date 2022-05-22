package com.mariolorian.wheelerdealer.api.config;

import com.mariolorian.wheelerdealer.api.NbpFeignClient;
import com.mariolorian.wheelerdealer.service.NbpCurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class NbpCurrencyServiceConfig {

    @Bean
    public NbpCurrencyService nbpCurrencyService(NbpFeignClient nbpFeignClient) {
        return new NbpCurrencyService(nbpFeignClient);
    }

}
