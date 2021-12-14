package com.project.petboard.domain.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByMemberNumber(Long memberNumber);
    Optional<Member> findByMemberSnsId(String memberSnsId);
    boolean existsByMemberEmail(String memberEmail);
}
