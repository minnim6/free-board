package com.project.petboard.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByRefreshToken(String refreshToken);
    boolean existsByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
}
