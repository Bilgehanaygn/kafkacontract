package com.bilge.returnorder.returnorder.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnOrderEvent implements Serializable {

  @Serial
  private static final long serialVersionUID = 1587253L;

  private UUID orderId;
  private UUID productId;
  private String productName;
  private BigDecimal price;
}
