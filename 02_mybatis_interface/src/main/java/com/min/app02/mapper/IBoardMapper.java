package com.min.app02.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.min.app02.dto.BoardDto;


@Mapper
public interface IBoardMapper {
  String now();
  List<BoardDto> selectBoardList();  
  BoardDto selectBoardById(@Param("boardId") int boardId);  
  int insertBoard(BoardDto boardDto);    
  int updateBoard(@Param("title") String title, @Param("contents") String contents, @Param("boardId") int boardId);
  int deleteBoard(@Param("boardId") int boardId);

}
