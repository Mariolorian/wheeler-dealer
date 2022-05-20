package com.mariolorian.wheelerdealer.api.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class NbpFeignConfig {

    private static final String HEADER_NAME = "Accept";
    private static final String HEADER_VALUE = "application/json";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(HEADER_NAME, HEADER_VALUE);
    }

}
