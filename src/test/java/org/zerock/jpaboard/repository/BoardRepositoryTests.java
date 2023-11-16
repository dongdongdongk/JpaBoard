package org.zerock.jpaboard.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoards() {
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    //pk writer 가 반드시 있어야 한다는걸 주의
                    .writer(member)
                    .build();
            boardRepository.save(board);
        });
    }

}
