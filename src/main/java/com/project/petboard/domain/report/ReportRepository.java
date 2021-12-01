package com.project.petboard.domain.report;

import com.project.petboard.domain.member.Member;
import com.project.petboard.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report,Long> {
    Boolean existsByMemberAndPost(Member member, Post post);
}
