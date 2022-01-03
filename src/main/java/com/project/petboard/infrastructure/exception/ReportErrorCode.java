package com.project.petboard.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportErrorCode implements ErrorCode{
    REPORT_DUPLICATION(400,"400","이미 신고한적이 있는 게시물입니다.");
    private int status;
    private String errorCode;
    private String message;
}
