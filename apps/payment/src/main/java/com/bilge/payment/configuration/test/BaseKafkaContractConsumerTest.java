package com.bilge.payment.configuration.test;

import com.bilge.payment.configuration.kafka.KafkaConsumerConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.stubrunner.StubTrigger;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;


@ExtendWith(SpringExtension.class)
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ContextConfiguration(classes = {KafkaAutoConfiguration.class, KafkaContractConsumerTestConfig.class})
public abstract class BaseKafkaContractConsumerTest {

  @Autowired
  protected StubTrigger stubTrigger;

  @ServiceConnection
  static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
    .withKraft();


  static {
    kafka.start();
    System.setProperty("spring.kafka.bootstrap-servers", kafka.getBootstrapServers());
    System.out.println("blabla");
  }

}
