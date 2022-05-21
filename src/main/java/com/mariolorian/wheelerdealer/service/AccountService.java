package com.mariolorian.wheelerdealer.service;

import com.mariolorian.wheelerdealer.model.dto.AccountDto;
import com.mariolorian.wheelerdealer.model.dto.NewClientDto;
import com.mariolorian.wheelerdealer.model.entity.SubAccount;
import com.mariolorian.wheelerdealer.enums.Currency;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    boolean existsById(String id);

    String createNewAccount(NewClientDto clientDto);

    AccountDto receiveAccountDetails(String id);

    List<SubAccount> receiveSubAccounts(String id);

    void updateSubAccounts(String id, Currency currency, BigDecimal amount);

}
