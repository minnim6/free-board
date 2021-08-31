package com.project.petboard.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member , Long> {
    List<Member> findByNickname(String nickname);
}
