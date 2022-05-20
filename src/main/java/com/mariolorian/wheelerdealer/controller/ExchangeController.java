package com.mariolorian.wheelerdealer.controller;

import com.mariolorian.wheelerdealer.model.dto.ExchangeDto;
import com.mariolorian.wheelerdealer.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService service;

    @PostMapping("/exchange")
    public ResponseEntity<String> addNewClient(@RequestBody @Valid ExchangeDto exchangeDto) {
        Objects.requireNonNull(exchangeDto);
        String balanceValue = service.exchangeBalance(exchangeDto);
        return ResponseEntity.ok(balanceValue);
    }

}
