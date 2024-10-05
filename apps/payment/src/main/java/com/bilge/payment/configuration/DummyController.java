package com.bilge.payment.configuration;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dummy")
public class DummyController {

  @GetMapping("/dummy")
  public void dummy(){
    System.out.println("dummy");
  }
}
