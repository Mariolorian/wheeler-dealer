package com.mariolorian.wheelerdealer.model.dto;

import com.mariolorian.wheelerdealer.api.validation.LegalAgeConstraint;
import lombok.Value;
import org.hibernate.validator.constraints.pl.PESEL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
public class NewClientDto {

    @NotNull
    @PESEL
    @LegalAgeConstraint
    String pesel;

    @NotNull
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Invalid input. First name should contains only letter")
    String firstName;

    @NotNull
    @Pattern(regexp = "^[A-Z][a-z]*$|^[A-Z][a-z]*-[A-Z][a-z]*$", message = "Invalid input. Last name should contains only letters")
    String lastName;

    @NotNull
    @Pattern(regexp = "^\\d*[.]\\d{2}", message = "Invalid input")
    String initialBalanceInPln;

}
