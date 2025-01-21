package com.min.app04.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.min.app04.dto.BoardDto;
import com.min.app04.service.IBoardService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AsyncController1 {

  private final IBoardService boardService;
  
  // Spring Boot 프로젝트의 Spring Web 디펜던시에는
  // jackson-databind 라이브러리가 포함되어 있습니다. (Spring MVC Project 에서는 직접 pom.xml에 추가해야 합니다.)
  // 이 라이브러리가 Java 데이터와 JSON 데이터의 상호 변환을 담당합니다.
  
  @GetMapping(value={"/board/list", "/board/list.json"}, produces="application/json")   // 이 메소드의 응답 데이터는 JSON 타입입니다.
  @ResponseBody   // 반환 값을 요청한 곳으로 응답합니다. 페이지의 이동은 없습니다.
  public List<BoardDto> list(HttpServletRequest request) {    
    Map<String, Object> map = boardService.getBoardList(request);    
    return (List<BoardDto>)map.get("boardList");
  }
  
  // 응답데이터를 XML 데이터로 제공할 수 있습니다.
  // jackson-dataformat-xml 디펜던시가 이 역할을 수행할 수 있습니다.
  // 별도로 pom.xml에 추가해야 합니다.
  
  @GetMapping(value="/board/list.xml", produces="application/xml")
  @ResponseBody
  public Map<String, Object> listXml(HttpServletRequest request) {  
    return boardService.getBoardList(request);
  }
  
  
  
  
  
  
  
  
  
}
