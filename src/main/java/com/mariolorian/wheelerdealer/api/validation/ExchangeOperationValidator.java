package com.mariolorian.wheelerdealer.api.validation;

import com.mariolorian.wheelerdealer.enums.Operation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExchangeOperationValidator implements ConstraintValidator<ExchangeOperationConstraint, String> {

    @Override
    public boolean isValid(String operation, ConstraintValidatorContext context) {
        return Operation.ASK.equals(Operation.valueOf(operation)) || Operation.BID.equals(Operation.valueOf(operation));
    }

}
