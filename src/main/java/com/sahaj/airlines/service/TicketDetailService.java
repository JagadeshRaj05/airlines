package com.sahaj.airlines.service;

import com.sahaj.airlines.model.TicketDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketDetailService {

  private final Validator beanValidator;

  public void validateAndPopulateDiscountCode(List<TicketDetail> ticketDetails) {
    ticketDetails.stream()
        .map(ticketDetail -> {
          Set<ConstraintViolation<TicketDetail>> validate = beanValidator.validate(ticketDetail);
          String errorMessage = validate.stream().map(ConstraintViolation::getMessage)
              .collect(Collectors.joining(","));
          return ticketDetail.withError(errorMessage);
        })
        .filter(ticketDetail -> !StringUtils.hasLength(ticketDetail.getError()))
        .forEach(this::deriveDiscountCode);
  }

  private void deriveDiscountCode(TicketDetail ticketDetail) {
    int fareClass = ticketDetail.getFareClass();
    String discountCode = null;

    if (fareClass >= 65 && fareClass <= 69) {
      discountCode = "OFFER_20";
    } else if (fareClass >= 70 && fareClass <= 75) {
      discountCode = "OFFER_30";
    } else if (fareClass >= 76 && fareClass <= 82) {
      discountCode = "OFFER_25";
    }

    ticketDetail.updateDiscountCode(discountCode);
  }
}
