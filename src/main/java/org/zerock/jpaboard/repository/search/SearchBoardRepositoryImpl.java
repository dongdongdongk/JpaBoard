package org.zerock.jpaboard.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.QBoard;

import java.util.List;

@Slf4j
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board Search1() {

        log.info("search1...............................");

        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.select(board).where(board.bno.eq(1L));

        log.info("---------------------------");
        log.info("====={}=====",jpqlQuery);
        log.info("---------------------------");


        List<Board> result = jpqlQuery.fetch();


        return null;

    }
}
