package org.zerock.jpaboard.service;

import org.zerock.jpaboard.dto.BoardDTO;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);

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
}
