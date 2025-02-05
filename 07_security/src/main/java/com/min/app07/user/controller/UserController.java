package com.min.app07.user.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.min.app07.user.dto.SignupDto;
import com.min.app07.user.service.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
  
  private final IUserService userService;
  
  @GetMapping("/user/signup")
  public void signup() { }
  
  @PostMapping("/user/signup")
  public String signup(SignupDto signupDto, Model model) {
    Map<String, String> map = userService.signup(signupDto);
    model.addAttribute("message", map.get("message"));
    return map.get("path");
  }

}
