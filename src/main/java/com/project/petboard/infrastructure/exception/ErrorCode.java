package com.project.petboard.infrastructure.exception;

public interface ErrorCode {
    public String getMessage();
    public int getStatus();
    public String getErrorCode();
}
