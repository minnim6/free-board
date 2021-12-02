package com.project.petboard.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByMemberNumber(Long memberNumber);
}
