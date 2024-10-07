package com.bilge.payment.configuration.test;

import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.messaging.Message;

import javax.annotation.Nullable;
import java.util.Map;

public class KafkaContractConsumerTestConfig {

  @Lazy
  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  @Lazy
  private KafkaAdmin kafkaAdmin;

  @Bean
  MessageVerifierSender<?> kafkaMessageVerifierSender() {
    return new MessageVerifierSender<>() {
      @Override
      public void send(final Object message, final String destination, @Nullable final YamlContract contract) {
        send(message, Map.of(), destination, contract);
      }

      @SneakyThrows
      @Override
      public void send(Object payload, Map headers, String destination, @Nullable YamlContract contract) {
        kafkaAdmin.createOrModifyTopics(TopicBuilder.name(destination).build());
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(destination, payload);
        kafkaTemplate.send(producerRecord).get();
      }
    };
  }

  @Bean
  @Primary
  JsonMessageConverter noopMessageConverter() {
    return new NoopJsonMessageConverter();
  }
}

class NoopJsonMessageConverter extends JsonMessageConverter {

  NoopJsonMessageConverter() {
  }

  @Override
  protected Object convertPayload(Message<?> message) {
    return message.getPayload();
  }
}