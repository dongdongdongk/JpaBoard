package org.zerock.jpaboard.service;

import org.zerock.jpaboard.dto.BoardDTO;
import org.zerock.jpaboard.dto.PageRequestDTO;
import org.zerock.jpaboard.dto.PageResultDTO;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.Member;

public interface BoardService {

    // 게시물 등록
    Long register(BoardDTO dto);

    // 리스트 목록 처리
    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    // 게시물 상세 조회
    BoardDTO get(Long bno);

    // 게시물 삭제
    void removeWithReplies(Long bno); // 삭제기능

    // 게시물 수정
    void modify(BoardDTO boardDTO);


    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // int 로 처리하도록
                .build();

        return boardDTO;

    }
}
