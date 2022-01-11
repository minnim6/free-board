package com.project.petboard.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    void deleteAllByMember(Member member);
}
