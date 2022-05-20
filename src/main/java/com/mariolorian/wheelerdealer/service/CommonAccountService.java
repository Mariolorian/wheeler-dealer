package com.mariolorian.wheelerdealer.service;

import com.mariolorian.wheelerdealer.exception.NoSuchAccountFoundException;
import com.mariolorian.wheelerdealer.exception.UserWithSuchPeselAlreadyExistedException;
import com.mariolorian.wheelerdealer.model.dto.AccountDto;
import com.mariolorian.wheelerdealer.model.dto.NewClientDto;
import com.mariolorian.wheelerdealer.model.entity.Account;
import com.mariolorian.wheelerdealer.model.entity.Client;
import com.mariolorian.wheelerdealer.model.entity.SubAccount;
import com.mariolorian.wheelerdealer.enums.Currency;
import com.mariolorian.wheelerdealer.repository.AccountRepository;
import com.mariolorian.wheelerdealer.repository.ClientRepository;
import com.mariolorian.wheelerdealer.repository.SubAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonAccountService implements AccountService {

    private final AccountRepository repository;
    private final ClientRepository clientRepository;
    private final SubAccountRepository subAccountRepository;

    @Override
    public boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Transactional
    @Override
    public String addNewAccount(NewClientDto clientDto) {
        Objects.requireNonNull(clientDto);
        if (clientRepository.existsByPesel(clientDto.getPesel())) {
            log.error("User with a given PESEL is already registered");
            throw new UserWithSuchPeselAlreadyExistedException();
        }
        SubAccount plnSubAccount = SubAccount.builder()
                .currency(Currency.PLN)
                .balance(new BigDecimal(clientDto.getInitialBalanceInPln()))
                .build();
        SubAccount usdSubAccount = SubAccount.builder()
                .currency(Currency.USD)
                .balance(new BigDecimal("00.00"))
                .build();
        List<SubAccount> subAccounts = List.of(plnSubAccount, usdSubAccount);
        Client client = Client.builder()
                .pesel(clientDto.getPesel())
                .firstName(clientDto.getFirstName())
                .lastName(clientDto.getLastName())
                .build();
//        Account account = new Account(client.getPesel(), client, subAccounts);
//        Account accountFromDB = repository.save(account);
        List<SubAccount> subAccountList = new ArrayList<>();
        subAccountRepository.saveAll(subAccounts).iterator().forEachRemaining(subAccountList::add);
        Client client1 = clientRepository.save(client);
        Account account = repository.save(new Account(client1.getPesel(), client1, subAccountList));
        return account.getId();
    }

    @Override
    public AccountDto receiveAccountDetails(String id) {
        return repository.findById(id).map(
                        account -> AccountDto.builder()
                                .id(account.getId())
                                .client(AccountDto.ClientDto.builder()
                                        .pesel(account.getClient().getPesel())
                                        .firstName(account.getClient().getFirstName())
                                        .lastName(account.getClient().getLastName())
                                        .build())
                                .subAccounts(account.getSubAccounts().stream().map(subAccount -> AccountDto.SubAccountDto.builder()
                                                .currency(subAccount.getCurrency())
                                                .balance(subAccount.getBalance().setScale(2, RoundingMode.HALF_EVEN).toString())
                                                .build())
                                        .toList())
                                .build())
                .orElseThrow(NoSuchAccountFoundException::new);
    }

    @Override
    public List<SubAccount> receiveSubAccounts(String id) {
        return subAccountRepository.findAllByAccountId(id);
    }

    @Override
    @Transactional
    public void updateSubAccounts(String id, Currency currency, BigDecimal amount) {
        Optional<List<SubAccount>> maybeSubAccounts = repository.findById(id).map(Account::getSubAccounts);
        if (maybeSubAccounts.isPresent()) {
            Optional<SubAccount> maybeSubAccount = maybeSubAccounts.get().stream()
                    .filter(subAccount -> currency.equals(subAccount.getCurrency()))
                    .findFirst();
            if (maybeSubAccount.isPresent()) {
                SubAccount subAccount = maybeSubAccount.get();
                subAccount.setBalance(subAccount.getBalance().add(amount));
                subAccountRepository.save(subAccount);
            }
        }
    }

}
