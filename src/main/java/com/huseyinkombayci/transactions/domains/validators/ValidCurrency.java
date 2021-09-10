package com.huseyinkombayci.transactions.domains.validators;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@Constraint(validatedBy = CurrencyValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrency {
  String message() default "The input should be GBP, EUR, or USD.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}

class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return List.of("GBP", "EUR", "USD").contains(value);
  }
}