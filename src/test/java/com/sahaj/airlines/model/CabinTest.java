package com.sahaj.airlines.model;

import org.junit.jupiter.api.Test;

import static com.sahaj.airlines.model.Cabin.PREMIUM_ECONOMY;
import static org.junit.jupiter.api.Assertions.*;

class CabinTest {

  @Test
  void shouldReturnCabinByDesc() {
    // Prepare
    String desc = "Premium Economy";

    // Act & Assert
    assertEquals(PREMIUM_ECONOMY, Cabin.getByDesc(desc));
  }

  @Test
  void shouldReturnNullIfDescIsInvalid() {
    // Prepare
    String desc = "Invalid Economy";

    // Act & Assert
    assertNull(Cabin.getByDesc(desc));
  }

  @Test
  void shouldReturnAllDescCommaSeparated() {
    assertEquals("Economy,Premium Economy,Business,First", Cabin.getAllCabinCommaSeparated());
  }

}