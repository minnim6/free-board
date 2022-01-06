package com.project.petboard.infrastructure.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class RequestErrorException extends RuntimeException{

    private List<ObjectError> errorList;

    public RequestErrorException(BindingResult bindingResult){
        errorList = bindingResult.getAllErrors();
    }
}
