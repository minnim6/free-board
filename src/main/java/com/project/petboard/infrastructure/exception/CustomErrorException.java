package com.project.petboard.infrastructure.exception;

import lombok.Getter;

@Getter
public class CustomErrorException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomErrorException(String message, ErrorCode errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public CustomErrorException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
}
