package org.zerock.jpaboard.service;

import org.zerock.jpaboard.dto.ReplyDTO;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register (ReplyDTO replyDTO); // 댓글 등록

    List<ReplyDTO> getList(Long bno); // 특정 게시물의 댓글 목록

    void modify(ReplyDTO replyDTO); // 댓글 수정

    void remove(Long rno); // 댓글 삭제

    // ReplyDTO 를  Reply 객체로 변환 Board 객체의 처리가 수반됨
    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board) // Board 객체의 처리
                .build();

        return reply;
    }

    // Reply 객체를 ReplyDTO 로 변환 Board 객체가 필요하지 않으므로 게시물 번호만
    default ReplyDTO entityToDTO(Reply reply) {
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();

        return dto;
    }
}
