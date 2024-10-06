package com.bilge.returnorder.configuration.test;

import com.bilge.returnorder.configuration.kafka.KafkaTopicsConfig;
import com.bilge.returnorder.configuration.ReturnOrderProducer;
import com.bilge.returnorder.returnorder.domain.ReturnOrderEvent;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.UUID;


@ExtendWith(SpringExtension.class)
@AutoConfigureMessageVerifier
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.NONE,
  classes = {KafkaProducerTestConfig.class, KafkaTopicsConfig.class}
)
@Testcontainers
@Import(ReturnOrderProducer.class)
public abstract class BaseKafkaProducerContractTest {

  @Autowired
  private ReturnOrderProducer returnOrderProducer;

  @ServiceConnection
  static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
    .withKraft();


  static {
    kafka.start();
    System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
  }

  public void publishReturnOrderEvent() {
    var returnOrderEvent = new ReturnOrderEvent(UUID.randomUUID(), UUID.randomUUID(), "random product 1", BigDecimal.TEN);
    returnOrderProducer.publishReturnOrderEvent(returnOrderEvent);
  }
}
