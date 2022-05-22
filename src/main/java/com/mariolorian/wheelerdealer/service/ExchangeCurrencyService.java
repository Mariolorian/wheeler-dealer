package com.mariolorian.wheelerdealer.service;

import com.mariolorian.wheelerdealer.enums.Currency;
import com.mariolorian.wheelerdealer.enums.Operation;
import com.mariolorian.wheelerdealer.exception.NoSuchAccountFoundException;
import com.mariolorian.wheelerdealer.exception.NotEnoughFoundsException;
import com.mariolorian.wheelerdealer.model.dto.CurrencyDto;
import com.mariolorian.wheelerdealer.model.dto.ExchangeDto;
import com.mariolorian.wheelerdealer.model.entity.SubAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExchangeCurrencyService implements ExchangeService {

    private final CurrencyService nbpCurrencyResilientService;
    private final AccountService accountService;

    @Override
    @Transactional
    public String exchangeBalance(ExchangeDto exchangeDto) {
        Objects.requireNonNull(exchangeDto);
        if (!accountService.existsById(exchangeDto.getId())) {
            log.warn("There is no account with a given id");
            throw new NoSuchAccountFoundException();
        }
        List<SubAccount> subAccounts = accountService.receiveSubAccounts(exchangeDto.getId());
        SubAccount plnSubAccount = subAccounts.stream()
                .filter(subAccount -> subAccount.getCurrency().equals(Currency.PLN))
                .findFirst().get();
        SubAccount usdSubAccount = subAccounts.stream()
                .filter(subAccount -> subAccount.getCurrency().equals(Currency.USD))
                .findFirst().get();
        BigDecimal currentBalance = null;
        switch (Operation.valueOf(exchangeDto.getOperation())) {
            case ASK -> currentBalance = askPln(exchangeDto.getId(), exchangeDto.getBalance(), usdSubAccount);
            case BID -> currentBalance = bidPln(exchangeDto.getId(), exchangeDto.getBalance(), plnSubAccount);
        }
        return currentBalance.toString();
    }

    private BigDecimal askPln(String id, String currencyAmount, SubAccount subAccount) {
        BigDecimal currencyQuantity = new BigDecimal(currencyAmount);
        checkTransaction(currencyQuantity, subAccount.getBalance());
        BigDecimal currencyOutcome = askPln(currencyQuantity);
        log.info("Exchange: {} USD for {} PLN", currencyQuantity, currencyOutcome);
        accountService.updateSubAccounts(id, Currency.USD, currencyQuantity.negate());
        accountService.updateSubAccounts(id, Currency.PLN, currencyOutcome);
        return currencyOutcome;
    }

    private BigDecimal askPln(BigDecimal currency) {
        CurrencyDto currencyDto = nbpCurrencyResilientService.receiveCurrentCurrencyStatus();
        BigDecimal value = new BigDecimal(currencyDto.getRates().get(0).getBid().toString());
        return currency.multiply(value);
    }

    private BigDecimal bidPln(String id, String currencyAmount, SubAccount subAccount) {
        BigDecimal currencyQuantity = new BigDecimal(currencyAmount);
        checkTransaction(currencyQuantity, subAccount.getBalance());
        BigDecimal currencyOutcome = bidPln(currencyQuantity);
        log.info("Exchange: {} PLN for {} USD", currencyQuantity, currencyOutcome);
        accountService.updateSubAccounts(id, Currency.PLN, currencyQuantity.negate());
        accountService.updateSubAccounts(id, Currency.USD, currencyOutcome);
        return currencyOutcome;
    }

    private BigDecimal bidPln(BigDecimal currency) {
        CurrencyDto currencyDto = nbpCurrencyResilientService.receiveCurrentCurrencyStatus();
        BigDecimal value = new BigDecimal(currencyDto.getRates().get(0).getAsk().toString());
        return currency.divide(value, 2, RoundingMode.HALF_EVEN);
    }

    private void checkTransaction(BigDecimal currencyQuantity, BigDecimal subAccount) {
        int status = currencyQuantity.compareTo(subAccount);
        if (status > 0) {
            log.warn("There is no enough funds to conduct a transaction");
            throw new NotEnoughFoundsException();
        }
    }

}
