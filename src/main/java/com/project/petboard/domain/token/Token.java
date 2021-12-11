package com.project.petboard.domain.token;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Token {

    @Id
    private String refreshToken;

    private String accessToken;

    @Temporal(TemporalType.DATE)
    private Date  refreshTokenExpireTime;
}
