package com.bilge.returnorder.returnorder.application;

import com.bilge.returnorder.configuration.ReturnOrderProducer;
import com.bilge.returnorder.returnorder.domain.ReturnOrderEvent;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ReturnOrderService {

  private final ReturnOrderProducer returnOrderProducer;

  public ReturnOrderService(ReturnOrderProducer returnOrderProducer) {
    this.returnOrderProducer = returnOrderProducer;
  }

  public String create() {
    ReturnOrderEvent event = new ReturnOrderEvent(UUID.randomUUID(), UUID.randomUUID(), "T-shirt", new BigDecimal("99.99"));

    returnOrderProducer.publishReturnOrderEvent(event);

    return "ReturnOrderEvent published!";
  }
}
