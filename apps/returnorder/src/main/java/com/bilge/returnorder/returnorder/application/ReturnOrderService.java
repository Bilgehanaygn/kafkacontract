package com.bilge.returnorder.returnorder.application;

import com.bilge.returnorder.configuration.KafkaProducerService;
import com.bilge.returnorder.returnorder.domain.ReturnOrderEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ReturnOrderService {

  private final KafkaProducerService kafkaProducerService;

  public ReturnOrderService(KafkaProducerService kafkaProducerService) {
    this.kafkaProducerService = kafkaProducerService;
  }

  public String create() {
    ReturnOrderEvent event = new ReturnOrderEvent(UUID.randomUUID(), UUID.randomUUID(), "T-shirt", new BigDecimal("99.99"));

    kafkaProducerService.publishReturnOrderEvent(event);

    return "ReturnOrderEvent published!";
  }
}
