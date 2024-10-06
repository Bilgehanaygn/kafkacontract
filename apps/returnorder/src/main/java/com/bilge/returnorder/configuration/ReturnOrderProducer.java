package com.bilge.returnorder.configuration;

import com.bilge.returnorder.configuration.kafka.BaseProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.bilge.returnorder.returnorder.domain.ReturnOrderEvent;

@Service
public class ReturnOrderProducer implements BaseProducer {

  private static final String TOPIC = "return-order";

  private final KafkaTemplate<String, ReturnOrderEvent> kafkaTemplate;

  public ReturnOrderProducer(KafkaTemplate<String, ReturnOrderEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publishReturnOrderEvent(ReturnOrderEvent returnOrderEvent) {
    kafkaTemplate.send(TOPIC, returnOrderEvent.getOrderId().toString(), returnOrderEvent);
  }

  @Override
  public String getTopic() {
    return TOPIC;
  }
}


