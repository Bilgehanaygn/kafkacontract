package com.bilge.payment.configuration.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig<T extends Serializable> {

  private final KafkaProperties kafkaProperties;
  private final Class<T> targetType;

  public KafkaConsumerConfig(KafkaProperties kafkaProperties, Class<T> targetType) {
    this.kafkaProperties = kafkaProperties;
    this.targetType = targetType;
  }

  @Bean
  public ConsumerFactory<String, T> consumerFactory() {
    Map<String, Object> config = new HashMap<>(kafkaProperties.buildConsumerProperties());

    config.put(ConsumerConfig.GROUP_ID_CONFIG, "my-group");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

    JsonDeserializer<T> deserializer = new JsonDeserializer<>(targetType)
      .ignoreTypeHeaders()
      .trustedPackages("*");

    return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, T> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, T> factory =
      new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
