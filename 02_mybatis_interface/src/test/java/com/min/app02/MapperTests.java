package com.min.app02;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.min.app02.dto.BoardDto;
import com.min.app02.mapper.IBoardMapper;

@SpringBootTest
class MapperTests {

  @Autowired
  IBoardMapper boardMapper;
  
	@Test
	void now_test() {	  
	  Assertions.assertThat(boardMapper.now().split(" ")[0]).isEqualTo("2025-01-17");	  
	}

	 @Test
	  void selectList_test() {   
	    Assertions.assertThat(boardMapper.selectBoardList().size()).isEqualTo(1);   
	  }

   @Test
   void selectById_test() {   
     Assertions.assertThat(boardMapper.selectBoardById(1).getTitle()).isEqualTo("첫번째 게시글");   
   }

   @Test
   void insert_test() {   
     BoardDto boardDto = BoardDto.builder()
                                 .title("테스트 제목")
                                 .contents("테스트 내용")
                                 .build();
     Assertions.assertThat(boardMapper.insertBoard(boardDto)).isEqualTo(1);   
     System.out.println("~~~~~" + boardDto.getBoardId());   //삽입된 key값이 출력
   }

   @Test
   void update_test() {   
     Assertions.assertThat(boardMapper.updateBoard("수정제목", "수정내용", 1)).isEqualTo(1);   
   }

   @Test
   void delete_test() {   
     Assertions.assertThat(boardMapper.deleteBoard(1)).isEqualTo(1);   
   }

}
