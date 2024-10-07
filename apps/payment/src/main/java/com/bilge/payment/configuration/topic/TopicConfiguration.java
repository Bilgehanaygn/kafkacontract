package com.bilge.payment.configuration.topic;

import com.bilge.payment.configuration.kafka.KafkaConsumerConfig;
import com.bilge.payment.refund.consumers.ReturnOrderEvent;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfiguration {
  private final KafkaProperties kafkaProperties;

  public TopicConfiguration(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  @Bean
  public KafkaConsumerConfig<ReturnOrderEvent> kafkaConsumerConfig() {
    return new KafkaConsumerConfig<>(kafkaProperties, ReturnOrderEvent.class);
  }
}
