package com.sahaj.airlines.model.validator;

import com.sahaj.airlines.model.validator.impl.TicketDetailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE_USE;

@Target({TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = TicketDetailValidator.class)
public @interface TicketDetailValidation {
  public String message() default "Invalid ticket details";
  public Class<?>[] groups() default {};
  public Class<? extends Payload>[] payload() default {};
}
