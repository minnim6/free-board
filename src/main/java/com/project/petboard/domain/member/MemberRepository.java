package com.project.petboard.domain.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @EntityGraph(attributePaths = "memberRole",type = EntityGraph.EntityGraphType.LOAD)
    Member findByMemberNumber(Long memberNumber);

    @EntityGraph(attributePaths = "memberRole",type = EntityGraph.EntityGraphType.LOAD)
    Optional<Member> findByMemberSnsId(String memberSnsId);

    boolean existsByMemberEmail(String memberEmail);

    boolean existsByMemberRefreshToken(String refreshToken);
}
