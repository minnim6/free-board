package com.project.petboard.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@AllArgsConstructor
public enum HttpErrorCode implements ErrorCode{
    NOT_FOUND(404,"404","page_not_found"),
    SERVER_ERROR(500,"500","server_error_reponses"),
    BAD_REQUEST(400,"400","bad_requset");
    private int status;
    private String errorCode;
    private String message;
}
