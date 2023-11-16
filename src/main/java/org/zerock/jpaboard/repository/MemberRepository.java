package org.zerock.jpaboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.jpaboard.entity.Member;

public interface MemberRepository extends JpaRepository<Member,String> {
}
