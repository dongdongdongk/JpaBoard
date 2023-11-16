package org.zerock.jpaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.jpaboard.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
