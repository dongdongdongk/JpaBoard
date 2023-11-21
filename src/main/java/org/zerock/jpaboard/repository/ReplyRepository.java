package org.zerock.jpaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.jpaboard.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Modifying //  @Query 어노테이션으로 작성된 조회를 제외한 데이터에 변경이 일어나는 삽입('INSERT'), 수정('UPDATE'), 삭제('DELETE') 쿼리 메서드를 사용할 때 필요하다 (데이터베이스에 변경 작업을 수행한다는것)
    @Query("delete from Reply r where r.board.bno = :bno")
    void deleteByBno(Long bno);
}
