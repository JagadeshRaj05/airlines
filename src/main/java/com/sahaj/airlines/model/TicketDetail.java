package com.sahaj.airlines.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sahaj.airlines.common.StringUtils;
import com.sahaj.airlines.model.validator.TicketDetailValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
@TicketDetailValidation
public class TicketDetail {
  @NotBlank(message = "First name should not be blank")
  private String firstName;
  @NotBlank(message = "Last name should not be blank")
  private String lastName;
  private String pnr;
  private Character fareClass;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate travelDate;
  @NotNull(message = "Pax should not be null")
  private Integer pax;
  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate dateOfBooking;
  @Email(regexp = "^(.+)@(.+)$", message = "Email is invalid")
  private String email;
  private String mobile;
  private Cabin cabin;

  private String error;
  private String discountCode;

  public TicketDetail withError(String error) {
    this.error = error;
    return this;
  }

  public void updateDiscountCode(String discountCode) {
    this.discountCode = discountCode;
  }

  @Override
  public String toString() {
    return StringUtils.toString(this);
  }
}
