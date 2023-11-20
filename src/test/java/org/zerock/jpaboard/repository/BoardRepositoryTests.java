package org.zerock.jpaboard.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.Member;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    // 게시판 더미데이터 입력
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

    // 게시글 조회 테스트
    @Test
    @Transactional
    public void testRead1() {
        Optional<Board> result = boardRepository.findById(100L); // 데이터베이스에 존재하는 번호

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }

    // 게시글 + 작성자 조회
    @Test
    public void testJoin1() {

        Object result = boardRepository.getBoardWithWriter(100L);

        System.out.println(result);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    // 게시글 + 댓글수 조회
    @Test
    public void testJoin2() {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {

            System.out.println(Arrays.toString(arr));

        }

    }

    // 게시글 + 댓글 수 조회 페이저
    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row -> {

            Object[] arr = (Object[])row;

            System.out.println(Arrays.toString(arr));
        });
    }

    //게시글 + 댓글 + 작성자 게시글 상세 조회
    @Test
    public void testRead3() {

        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[])result;

        System.out.println(Arrays.toString(arr));
    }


}
