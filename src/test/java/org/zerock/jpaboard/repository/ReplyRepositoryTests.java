package org.zerock.jpaboard.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1,300).forEach(i -> {
            // 1 부터 100까지의 임의의 번호 0.0, 0.1, 0.2, 0.3, 1.5, 3.6, 5.7, 8.3 9.9 이런식으로 숫자 생성 x100 + 1
            Long bno = (long)(Math.random() * 100) + 1; // (long) 으로 형변환 int 니까
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Reply....." + i)
                    .board(board)
                    .replyer("guest")
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    public void readReply1() {

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);

        System.out.println(reply.getBoard());

    }

    // 게시글 97번의 댓글 가져오기
    @Test
    public void testListByBoard() {
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build()); //Builder 로 객체생성

        replyList.forEach(reply -> System.out.println(reply));
    }

}
