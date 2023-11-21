package org.zerock.jpaboard.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.jpaboard.dto.BoardDTO;
import org.zerock.jpaboard.dto.PageRequestDTO;
import org.zerock.jpaboard.dto.PageResultDTO;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.Member;
import org.zerock.jpaboard.repository.BoardRepository;
import org.zerock.jpaboard.repository.ReplyRepository;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository; // 자동주입 final

    private final ReplyRepository replyRepository; // 댓글 삭제를 위해서

    // 게시물 등록
    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board = dtoToEntity(dto);

        boardRepository.save(board);

        return board.getBno();

    }

    // 게시물 리스트 불러오기
    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board)en[0],(Member)en[1],(Long)en[2]));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(
                pageRequestDTO.getPageable(Sort.by("bno").descending()));


        return new PageResultDTO<>(result, fn);
    }

   //게시물 상세 조회
    @Override
    public BoardDTO get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    // 게시물 삭제기능 (댓글도 함께 지워짐)
    @Transactional
    @Override
    public void removeWithReplies(Long bno) { // 삭제기능 구현 꼭 트랜잭션 추가

        // 댓글부터 삭제
        replyRepository.deleteByBno(bno);
        // 게시글 삭제
        boardRepository.deleteById(bno);

    }

    // 게시글 수정
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {

        // 게시물 ID로 게시물 조회
        Optional<Board> optionalBoard = boardRepository.findById(boardDTO.getBno());

        // 게시물이 존재하는 경우에만
        if(optionalBoard.isPresent()){
            // Optional에서 실제 Board 객체를 추출 ( get 메서드는 Optional 객체 안에 포함된 값을 반환하는 메서드)
            Board board = optionalBoard.get();

            board.changeContent(boardDTO.getContent());
            board.changeTitle(boardDTO.getTitle());

            boardRepository.save(board);
        }
    }
}
