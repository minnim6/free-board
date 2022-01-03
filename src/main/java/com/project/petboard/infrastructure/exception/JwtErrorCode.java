package com.project.petboard.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements ErrorCode{
    TOKEN_EXPIRE(401,"401","TOKEN_EXPIRE");
    private int status;
    private String errorCode;
    private String message;
}
