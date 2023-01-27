package com.sahaj.airlines.service;

import com.sahaj.airlines.model.Cabin;
import com.sahaj.airlines.model.TicketDetail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketDetailServiceTest {

  @InjectMocks
  private TicketDetailService ticketDetailService;

  @Mock
  private Validator beanValidator;

  @Test
  void shouldProcessAndAddErrorMessagesEntityWise() {
    // Prepare
    List<TicketDetail> ticketDetails = List.of(
        new TicketDetail(null, "",
            "PNR876", 'A', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "Invalid pnr", 'A', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", '&', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'A', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-03"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'A', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abcabccom",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'A', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "1299320553", Cabin.BUSINESS, null, null));

    ConstraintViolation constraintViolation = mock(ConstraintViolation.class);
    when(beanValidator.validate(any())).thenReturn(Set.of(constraintViolation));
    when(constraintViolation.getMessage()).thenReturn("Error");

    // Act
    ticketDetailService.validateAndPopulateDiscountCode(ticketDetails);

    // assert
    assertTrue(ticketDetails.stream().noneMatch(ticketDetail -> Objects.nonNull(ticketDetail.getDiscountCode())));

    String actual = ticketDetails.stream().map(TicketDetail::getError).collect(Collectors.joining(","));
    assertEquals("Error,Error,Error,Error,Error,Error", actual);
  }

  @Test
  void shouldPopulateDiscountCodeUsingFareClass() {
    // Prepare
    List<TicketDetail> ticketDetails = List.of(new TicketDetail("First name", "Last name",
            "PNR876", 'A', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'E', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'F', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'K', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'L', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'R', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'S', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null),
        new TicketDetail("First name", "Last name",
            "PNR876", 'T', LocalDate.parse("2023-01-01"),
            2, LocalDate.parse("2023-01-01"), "abc@abc.com",
            "7299320553", Cabin.BUSINESS, null, null));

    // Act
    ticketDetailService.validateAndPopulateDiscountCode(ticketDetails);

    // Asssert
    assertTrue(ticketDetails.stream().noneMatch(ticketDetail -> StringUtils.hasLength(ticketDetail.getError())));

    String actual = ticketDetails.stream().map(TicketDetail::getDiscountCode).collect(Collectors.joining(","));
    assertEquals("OFFER_20,OFFER_20,OFFER_30,OFFER_30,OFFER_25,OFFER_25,null,null", actual);
  }

}