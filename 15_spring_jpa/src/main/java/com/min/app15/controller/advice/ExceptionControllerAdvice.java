package com.min.app15.controller.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.min.app15.model.exception.MenuNotFoundException;
import com.min.app15.model.message.ResponseErrorMessage;

@ControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(MenuNotFoundException.class)
  public ResponseEntity<ResponseErrorMessage> handler(MenuNotFoundException e) {
    
    return ResponseEntity.badRequest().body(ResponseErrorMessage.builder()
                  .code(10)
                  .message(e.getMessage())
                  .decribe("")
                  .build());
  }  
  
}
