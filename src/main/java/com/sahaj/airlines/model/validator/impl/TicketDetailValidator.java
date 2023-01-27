package com.sahaj.airlines.model.validator.impl;

import com.sahaj.airlines.common.StringUtils;
import com.sahaj.airlines.model.TicketDetail;
import com.sahaj.airlines.model.validator.TicketDetailValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.sahaj.airlines.common.StringUtils.isPatternMatching;
import static com.sahaj.airlines.model.Cabin.getAllCabinCommaSeparated;

public class TicketDetailValidator implements ConstraintValidator<TicketDetailValidation, TicketDetail> {
  public static final String MOBILE_REGEX_PATTERN = "(0/91)?[7-9][0-9]{9}";
  public static final String PNR_REGEX_PATTERN = "([a-zA-Z0-9]{6})";

  @Override
  public boolean isValid(TicketDetail value, ConstraintValidatorContext context) {
    List<String> errors = new ArrayList<>();

    if (!isPatternMatching(value.getMobile(), MOBILE_REGEX_PATTERN)) {
      errors.add("Mobile number is invalid");
    }

    if (value.getTravelDate().isBefore(value.getDateOfBooking())) {
      errors.add("Travel date is invalid. It should be in future to date of booking");
    }

    if (Objects.isNull(value.getFareClass())) {
      errors.add("Fare class should be A-Z");
    } else {
      int fareClass = value.getFareClass();
      if (!(fareClass >= 65 && fareClass <= 90)) {
        errors.add("Fare class should be A-Z");
      }
    }

    if (!isPatternMatching(value.getPnr(), PNR_REGEX_PATTERN)) {
      errors.add("PNR should be 6 alpha numeric characters");
    }

    if (Objects.isNull(value.getCabin())) {
      errors.add(String.format("Cabin should be one of %s", getAllCabinCommaSeparated()));
    }


    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(String.join(",", errors)).addConstraintViolation();
    return errors.isEmpty();
  }

}
