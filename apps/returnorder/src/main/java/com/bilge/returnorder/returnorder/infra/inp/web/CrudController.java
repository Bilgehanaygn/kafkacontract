package com.bilge.returnorder.returnorder.infra.inp.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bilge.returnorder.returnorder.application.ReturnOrderService;

@RestController
@RequestMapping("/api/orders")
public class CrudController {

  private final ReturnOrderService returnOrderService;

  public CrudController(ReturnOrderService returnOrderService) {
    this.returnOrderService = returnOrderService;
  }

  @PostMapping("/return")
  public String returnOrder() {
    return returnOrderService.create();
  }
}
