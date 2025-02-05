package com.min.app07.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  @GetMapping("/auth/login")
  public void login() { }
   
  @GetMapping("/admin/page")
  public void admin() { }
  
}
