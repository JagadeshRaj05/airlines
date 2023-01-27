package com.sahaj.airlines.model.validator.impl;

import com.sahaj.airlines.model.Cabin;
import com.sahaj.airlines.model.TicketDetail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketDetailValidatorTest {

  public static final String EMPTY = "";
  private static Validator beanValidator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    beanValidator = validatorFactory.getValidator();
  }

  public static Stream<Arguments> dataProvider() {
    return Stream.of(
        Arguments.of(
            "No error",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            EMPTY
        ),

        Arguments.of(
            "Not blank",
            new TicketDetail("", "Last Nme",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            "First name should not be blank"
        ),

        Arguments.of(
            "invalid pnr",
            new TicketDetail("First name", "Last name",
                "Invalid pnr", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            "PNR should be 6 alpha numeric characters"
        ),

        Arguments.of(
            "empty pnr",
            new TicketDetail("First name", "Last name",
                "", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            "PNR should be 6 alpha numeric characters"
        ),

        Arguments.of(
            "invalid fare class",
            new TicketDetail("First name", "Last name",
                "PNR876", '&', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            "Fare class should be A-Z"
        ),

        Arguments.of(
            "null fare class",
            new TicketDetail("First name", "Last name",
                "PNR876", null, LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            "Fare class should be A-Z"
        ),

        Arguments.of(
            "null pax",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                null, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            "Pax should not be null"
        ),

        Arguments.of(
            "travel date",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-03"), "abc@abc.com",
                "7299320553", Cabin.BUSINESS, null, null),
            "Travel date is invalid. It should be in future to date of booking"
        ),

        Arguments.of(
            "invalid email",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abcabccom",
                "7299320553", Cabin.BUSINESS, null, null),
            "Email is invalid"
        ),

        Arguments.of(
            "empty email",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "",
                "7299320553", Cabin.BUSINESS, null, null),
            "Email is invalid"
        ),

        Arguments.of(
            "invalid mobile",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "1299320553", Cabin.BUSINESS, null, null),
            "Mobile number is invalid"
        ),

        Arguments.of(
            "empty mobile",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "", Cabin.BUSINESS, null, null),
            "Mobile number is invalid"
        ),

        // check mobile
        Arguments.of(
            "cabin",
            new TicketDetail("First name", "Last name",
                "PNR876", 'A', LocalDate.parse("2023-01-01"),
                2, LocalDate.parse("2023-01-01"), "abc@abc.com",
                "7299320553", null, null, null),
            "Cabin should be one of Economy,Premium Economy,Business,First"
        )
    );
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("dataProvider")
  void shouldReturn(String name, TicketDetail ticketDetail, String expectedErrorMessage) {
    // Act
    String actualErrorMessage = beanValidator.validate(ticketDetail).stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.joining(","));

    assertEquals(expectedErrorMessage, actualErrorMessage);
  }

}