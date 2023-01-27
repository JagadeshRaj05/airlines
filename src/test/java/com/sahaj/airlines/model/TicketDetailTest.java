package com.sahaj.airlines.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketDetailTest {

  @Test
  void shouldUpdateErrorProperly() {
    // Prepare
    TicketDetail ticketDetail = new TicketDetail();

    // Act
    ticketDetail.withError("Error");

    // Assert
    assertEquals("Error", ticketDetail.getError());
  }

  @Test
  void shouldUpdateDiscountCodeProperly() {
    // Prepare
    TicketDetail ticketDetail = new TicketDetail();

    // Act
    ticketDetail.updateDiscountCode("OFFER_20");

    // Assert
    assertEquals("OFFER_20", ticketDetail.getDiscountCode());
  }

}