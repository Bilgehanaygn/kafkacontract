package com.bilge.payment.refund.application;

import com.bilge.payment.refund.consumers.ReturnOrderEvent;
import org.springframework.stereotype.Service;


@Service
public class RefundService {

  public void processReturnOrderAndRefund(ReturnOrderEvent returnOrderEvent){
    System.out.println("Received message: " + returnOrderEvent.toString());

  }
}
