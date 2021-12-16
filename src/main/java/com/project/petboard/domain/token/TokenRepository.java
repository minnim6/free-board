package com.project.petboard.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    boolean existsByAccessTokenAndRefreshToken(String accessToken,String refreshToken);
    Token findByRefreshToken(String refreshToken);
}
