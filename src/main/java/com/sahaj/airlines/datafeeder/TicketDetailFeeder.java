package com.sahaj.airlines.datafeeder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahaj.airlines.common.FileUtils;
import com.sahaj.airlines.model.TicketDetail;
import com.sahaj.airlines.service.TicketDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.sahaj.airlines.common.FileUtils.readData;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketDetailFeeder {

  private final ObjectMapper objectMapper;
  private final TicketDetailService ticketDetailService;

  @PostConstruct
  public void processTicketDetailFile() {
    log.info("***** Empezado *****");
    List<TicketDetail> ticketDetails = readData(
        objectMapper, "src/main/resources/ticket_details.json", new TypeReference<>() {
        });

    List<TicketDetail> errorTicketDetails = new ArrayList<>();
    List<TicketDetail> validTicketDetails = new ArrayList<>();

    ticketDetailService.validateAndPopulateDiscountCode(ticketDetails);
    ticketDetails.forEach(ticketDetail -> {
      if (StringUtils.hasLength(ticketDetail.getError())) {
        errorTicketDetails.add(ticketDetail);
        return;
      }

      validTicketDetails.add(ticketDetail);
    });


    log.info("Writing error ticket details in resource folder, size = {}", errorTicketDetails.size());
    FileUtils.write("src/main/resources/error_ticket_details.json", errorTicketDetails);

    log.info("Writing valid ticket details in resource folder, size = {}", validTicketDetails.size());
    FileUtils.write("src/main/resources/valid_ticket_details.json", validTicketDetails);

    log.info("***** Terminado *****");

    log.info("To reduce your time looking resources");
    log.info("****** Error ******");
    log.info(errorTicketDetails.toString());

    log.info("****** Valid ******");
    log.info(validTicketDetails.toString());
  }

}
