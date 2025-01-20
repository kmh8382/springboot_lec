package com.min.app03.service;

import java.util.Map;

import com.min.app03.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;

public interface IBoardService {

  Map<String, Object> getBoardList(HttpServletRequest request);
  BoardDto getBoardListById(int boardId);
  String registBoard(BoardDto boardDto);
  String modifyBoard(BoardDto boardDto);
  String removeBoard(int boardId);
  
}
