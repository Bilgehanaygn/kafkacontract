package com.bilge.payment.refund.consumers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderEvent implements Serializable {
  private UUID orderId;
  private UUID productId;
  private String productName;
  private Double price;
}
