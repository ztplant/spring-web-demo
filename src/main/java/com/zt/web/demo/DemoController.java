package com.zt.web.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  @GetMapping(path = "/demo/index")
  public String index(){
    return "This is index";
  }

}
