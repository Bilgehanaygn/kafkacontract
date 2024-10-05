package com.bilge.returnorder.configuration;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.bilge.returnorder.returnorder.domain.ReturnOrderEvent;

@Service
public class KafkaProducerService {

  private static final String TOPIC = "return-order";

  private final KafkaTemplate<String, ReturnOrderEvent> kafkaTemplate;

  public KafkaProducerService(KafkaTemplate<String, ReturnOrderEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publishReturnOrderEvent(ReturnOrderEvent returnOrderEvent) {
    kafkaTemplate.send(TOPIC, returnOrderEvent.getOrderId().toString(), returnOrderEvent);
    System.out.println("Published ReturnOrderEvent: " + returnOrderEvent);
  }
}
