package com.mariolorian.wheelerdealer.controller;

import com.mariolorian.wheelerdealer.model.dto.AccountDto;
import com.mariolorian.wheelerdealer.model.dto.NewClientDto;
import com.mariolorian.wheelerdealer.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService service;

    @PostMapping("/accounts")
    public ResponseEntity<String> addNewAccount(@RequestBody @Valid NewClientDto newClientDto) {
        Objects.requireNonNull(newClientDto);
        System.out.println(newClientDto);
        String id = service.addNewAccount(newClientDto);
        URI accountLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(accountLocation).build();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<AccountDto> receiveAccountDetails(@PathVariable @PESEL String id) {
        Objects.requireNonNull(id);
        AccountDto accountDto = service.receiveAccountDetails(id);
        return ResponseEntity.ok(accountDto);
    }

}
