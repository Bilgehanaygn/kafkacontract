package com.bilge.payment.contract;


import com.bilge.payment.configuration.test.BaseKafkaContractConsumerTest;
import com.bilge.payment.refund.application.RefundService;
import com.bilge.payment.refund.consumers.ReturnOrderListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@AutoConfigureStubRunner(
  ids = "com.bilge:returnorder:+:stubs:stubs",
  repositoryRoot = "file:/path/to/local/m2/repository",
  stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@SpringBootTest(classes = ReturnOrderListener.class)
public class ReturnOrderEventConsumerTest extends BaseKafkaContractConsumerTest {

  @MockBean
  RefundService refundService;

  @Test
  void returnOrderEventConsumesSuccessfully() {
    stubTrigger.trigger("returnOrderEvent");

    verify(refundService, timeout(10000).times(1)).processReturnOrderAndRefund(any());
  }
}

