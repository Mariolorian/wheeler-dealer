package com.mariolorian.wheelerdealer.model.dto;

import com.mariolorian.wheelerdealer.api.validation.ExchangeOperationConstraint;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class ExchangeDto {

    @NotNull
    @PESEL
    String id;

    @NotNull
    @ExchangeOperationConstraint
    String operation;

    @NotNull
    @Pattern(regexp = "^\\d*[.]\\d{2}", message = "Invalid input")
    String balance;

}
