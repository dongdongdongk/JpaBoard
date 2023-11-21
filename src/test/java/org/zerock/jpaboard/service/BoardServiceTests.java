package org.zerock.jpaboard.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.jpaboard.dto.BoardDTO;
import org.zerock.jpaboard.dto.PageRequestDTO;
import org.zerock.jpaboard.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    // 게시물 등록 테스트
    @Test
    public void testRegister() {
        BoardDTO boardDTO = BoardDTO.builder()
                .title("Test.....")
                .content("Test.....")
                .writerEmail("user55@aaa.com")
                .build();
        Long bno = boardService.register(boardDTO);
    }

    //게시물 리스트 조회
    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }

    }

    //게시물 상세 조회 테스트
    @Test
    public void testGet() {

        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    @Test
    public void testRemove() {

        Long bno = 1L;

        boardService.removeWithReplies(bno);

    }

}
