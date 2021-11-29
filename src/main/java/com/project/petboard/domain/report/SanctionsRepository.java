package com.project.petboard.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SanctionsRepository extends JpaRepository<Sanctions,String> {
}
