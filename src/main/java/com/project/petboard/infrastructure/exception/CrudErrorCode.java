package com.project.petboard.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CrudErrorCode implements ErrorCode{
    NULL_EXCEPTION(400,"400","필수 요소중 null이 있습니다.");
    private int status;
    private String errorCode;
    private String message;
}
