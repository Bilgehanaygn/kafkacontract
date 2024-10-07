package com.bilge.payment.refund.consumers;

import com.bilge.payment.refund.application.RefundService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class ReturnOrderListener {

  private final RefundService refundService;

  public ReturnOrderListener(RefundService refundService) {
    this.refundService = refundService;
  }

  @KafkaListener(topics = "return-order", groupId = "my-group")
  public void listen(Message<ReturnOrderEvent> message) {
    refundService.processReturnOrderAndRefund(message.getPayload());
  }
}
