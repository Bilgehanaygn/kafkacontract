package com.bilge.returnorder.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class KafkaTopicsConfig {

  private final ApplicationContext applicationContext;

  public KafkaTopicsConfig(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Bean
  public String[] allTopics() {
    Map<String, BaseProducer> producerBeans = applicationContext.getBeansOfType(BaseProducer.class);
    return producerBeans.values().stream()
      .map(BaseProducer::getTopic).distinct().toArray(String[]::new);
  }
}
