package org.zerock.jpaboard.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.jpaboard.entity.Board;
import org.zerock.jpaboard.entity.QBoard;
import org.zerock.jpaboard.entity.QMember;
import org.zerock.jpaboard.entity.QReply;

import java.util.List;

@Slf4j
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board Search1() {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board);

        log.info("---------------------------");
        log.info("tuple 객체 : {}",tuple);
        log.info("---------------------------");

        List<Tuple> result = tuple.fetch();

        log.info("result 객체 : {}", result);

        return null;

    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage.............................");

        // 도메인 클래스들
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        //JPQL 쿼리 객체 생성
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //SELECT b, w, count(r) FROM Board b
        //LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b

        // 부분적으로 필요하기 떄문에 tuple 사용
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        //검색 조건을 추가하기 위해 BooleanBuilder 추가
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //검색 조건을 추가 ( 검색 조건이 없을때는 내용물을 만들필요가 없기 때문에 bno 0 보다 크다로 끝
        BooleanExpression expression = board.bno.gt(0L);

        // 마지막에 groupBy 가 필요해서 사용
        tuple.groupBy(board);

        booleanBuilder.and(expression);

        // 검색 조건이 있다면
        if(type != null){
            String[] typeArr = type.split("");

            //검색 조건을 작성하기
            //컨디션 빌더를 따로 만들어서 검색 조건을 추가해준다
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t:typeArr) {
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            // 결과를 booleanBuilder 에 추가
            booleanBuilder.and(conditionBuilder);
        }

        // where 조건에 완성된 booleanBuilder 추가
        tuple.where(booleanBuilder);

        // groupBy board 추가
        tuple.groupBy(board);

        //결과를 list 에 담는다
        List<Tuple> result = tuple.fetch();

        log.info("==={}===",result);

        return null;
    }
}
