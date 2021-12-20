package com.project.petboard.domain.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Token {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long tokenNumber;

    private String refreshToken;

    @Temporal(TemporalType.DATE)
    private Date  refreshTokenExpireTime;

    @Builder
    public Token(String refreshToken,Date refreshTokenExpireTime){
        this.refreshToken = refreshToken;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

}
