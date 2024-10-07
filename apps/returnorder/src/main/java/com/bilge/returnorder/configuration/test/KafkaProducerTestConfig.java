package com.bilge.returnorder.configuration.test;

import com.bilge.returnorder.configuration.kafka.BaseProducer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

@TestConfiguration
public class KafkaProducerTestConfig {

  private final ApplicationContext applicationContext;

  public KafkaProducerTestConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public String[] allTopics() {
    Map<String, BaseProducer> producerBeans = applicationContext.getBeansOfType(BaseProducer.class);

    return producerBeans.values().stream()
      .map(BaseProducer::getTopic).distinct().toArray(String[]::new);
  }

  @Bean
  KafkaMessageVerifier kafkaTemplateMessageVerifier() {
    return new KafkaMessageVerifier();
  }
}