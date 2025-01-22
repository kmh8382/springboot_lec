package com.min.app04.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.min.app04.dto.BoardDto;
import com.min.app04.mapper.IBoardMapper;
import com.min.app04.util.PageUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements IBoardService {

  private final IBoardMapper boardMapper;
  private final PageUtil pageUtil;
  
  @Override
  public Map<String, Object> getBoardList(HttpServletRequest request) {
    
    // 페이징 처리를 위한 파라미터 page, display
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    // 페이징 처리를 위한 게시글 개수 count
    int count = boardMapper.selectBoardCount();
    
    // 페이징 처리에 필요한 모든 변수 처리하기
    pageUtil.setPaging(page, display, count);
    
    // offset
    int offset = pageUtil.getOffset();
    
    // 정렬을 위한 파라미터 sort
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    // 게시글 목록 가져오기 (전달 : offset, display, sort 를 저장한 Map)
    List<BoardDto> boardList = boardMapper.selectBoardList(Map.of("offset", offset
                                                                , "display", display
                                                                , "sort", sort));
    
    // 페이징 가져오기 (전달 : 정렬 방식/목록 개수/검색 같은 추가 파라미터들)
    String paging = pageUtil.getAsyncPaging(Map.of("display", display, "sort", sort));
    
    // 결과 반환하기 
    return Map.of("boardList", Map.of("board", boardList)
                , "count", count
                , "offset", offset
                , "paging", paging);
    
  }

  @Override
  public BoardDto getBoardById(int boardId) {
    return boardMapper.selectBoardById(boardId);
  }

  @Override
  public Map<String, Object> registBoard(BoardDto boardDto) {
    int status = 0;
    String msg = null;
    try {
      boardMapper.insertBoard(boardDto);
      status = 200;
      msg = "등록 성공";
    } catch (Exception e) {
      e.printStackTrace();
      status = 500;
      msg = "서버 오류 발생";
    }
    return Map.of("status", status
                , "msg", msg
                , "registed", boardDto);
  }

  @Override
  public String modifyBoard(BoardDto boardDto) {
    return boardMapper.updateBoard(boardDto) == 1 ? "수정 성공" : "수정 실패";
  }

  @Override
  public String removeBoard(int boardId) {
    return boardMapper.deleteBoard(boardId) == 1 ? "삭제 성공" : "삭제 실패";
  }

}
