package com.mariolorian.wheelerdealer.model.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CurrencyDto {

    String table;
    String currency;
    String code;
    List<RatesDto> rates;

    @Value
    @Builder
    public static class RatesDto {

        String no;
        String effectiveDate;
        Double bid;
        Double ask;
    }

}
