package com.mariolorian.wheelerdealer.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ExchangeOperationValidator.class)
@Target({ TYPE, FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface ExchangeOperationConstraint {

    String message() default "This operation is unknown";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
