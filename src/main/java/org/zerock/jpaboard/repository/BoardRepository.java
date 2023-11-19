package org.zerock.jpaboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.jpaboard.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    // 게시글 정보와 작성자 가져오기
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    // 게시판 정보와 댓글 정보 가져오기
    @Query("select b, r from Board b LEFT join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);

    // 게시글과 댓글 숫자 가져오기
    @Query(value ="SELECT b, w, count(r) " +
            " FROM Board b " +
            " LEFT JOIN b.writer w " +
            " LEFT JOIN Reply r ON r.board = b " +
            " GROUP BY b"
//            countQuery ="SELECT count(b) FROM Board b"
            )
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);


    // 게시글 정보 + 작성자 댓글 숫자 가져오기
    @Query("SELECT b, w, count(r) " +
            " FROM Board b LEFT JOIN b.writer w " +
            " LEFT OUTER JOIN Reply r ON r.board = b" +
            " WHERE b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);
}
