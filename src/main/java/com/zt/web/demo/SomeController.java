package com.zt.web.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/some")
public class SomeController {

  @GetMapping(path = "/index")
  public String some(){
    return "This is some index !";
  }

}
