package org.zerock.jpaboard.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.jpaboard.entity.Board;

public interface SearchBoardRepository {

    Board Search1();

    // 페이저 처리
    Page<Object[]> searchPage(String type, String keyword, Pageable pageable);
}
