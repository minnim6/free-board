package com.project.petboard.domain.post;

import com.project.petboard.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
    int countByPost(Post post);
    boolean countByPostAndMember(Member member, Post post);
}
