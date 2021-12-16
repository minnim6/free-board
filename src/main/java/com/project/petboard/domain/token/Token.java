package com.project.petboard.domain.token;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Token {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long tokenNumber;

    private String refreshToken;

    private String accessToken;

    @Temporal(TemporalType.DATE)
    private Date  refreshTokenExpireTime;
}
