package com.mariolorian.wheelerdealer.model.dto;

import com.mariolorian.wheelerdealer.enums.Currency;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AccountDto {

    String id;
    ClientDto client;
    List<SubAccountDto> subAccounts;

    @Value
    @Builder
    public static class ClientDto {

        //        private final Long id;
        String pesel;
        String firstName;
        String lastName;
    }

    @Value
    @Builder
    public static class SubAccountDto {

        //        private final Long id;
        Currency currency;
        String balance;
    }

}
