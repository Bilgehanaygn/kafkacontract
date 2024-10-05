package com.bilge.returnorder.configuration;

import lombok.SneakyThrows;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class KafkaMessageVerifier implements MessageVerifierReceiver<Message<?>> {

  private final Map<String, BlockingQueue<Message<?>>> broker = new ConcurrentHashMap<>();

  @Override
  public Message<?> receive(String destination, YamlContract contract) {
    return receive(destination, 15, TimeUnit.SECONDS, contract);
  }

  @Override
  @SneakyThrows
  public Message<?> receive(String destination, long timeout, TimeUnit timeUnit, YamlContract contract) {
    broker.putIfAbsent(destination, new ArrayBlockingQueue<>(10));
    BlockingQueue<Message<?>> messageQueue = broker.get(destination);

    return messageQueue.poll(timeout, timeUnit);
  }

  @KafkaListener(topics = "#{@allTopics}")
  public void listen(Message<?> payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    broker.putIfAbsent(topic, new ArrayBlockingQueue<>(10));
    BlockingQueue<Message<?>> messageQueue = broker.get(topic);
    messageQueue.add(payload);
  }
}
