package com.bilge.returnorder.configuration.test;

import com.bilge.returnorder.configuration.kafka.BaseProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
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
  public <T> ProducerFactory<String, T> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getProperty("spring.kafka.bootstrap-servers"));
    configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean
  public <T> KafkaTemplate<String, T> kafkaTemplate(ProducerFactory<String, T> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  KafkaMessageVerifier kafkaTemplateMessageVerifier() {
    return new KafkaMessageVerifier();
  }
}