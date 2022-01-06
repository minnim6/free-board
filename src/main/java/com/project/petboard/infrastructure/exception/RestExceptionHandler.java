package com.project.petboard.infrastructure.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomErrorException.class)
    public ResponseEntity<ErrorResponse> customException(CustomErrorException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(RequestErrorException.class)
    public ResponseEntity<ErrorResponse> requestException(RequestErrorException e){
        List<ObjectError> errorCodeList =  e.getErrorList();
        MultiValueMap<String,String> errorMap = new LinkedMultiValueMap<>();
        for(ObjectError objectError:errorCodeList){
            errorMap.add(objectError.getObjectName(),objectError.getDefaultMessage());
        }
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }
}
