package com.em.apiproductschallenge.advice;

import com.em.apiproductschallenge.exceptions.DomainNotFoundException;
import com.em.apiproductschallenge.utils.errors.ApiError;
import com.em.apiproductschallenge.utils.errors.ApiFieldError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
public class DomainAdvice {

    @ResponseBody
    @ExceptionHandler(DomainNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError DomainNotFoundException(DomainNotFoundException dominioNoEncontradoException){
        return new ApiError(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(),
                Collections.singletonList(new ApiFieldError(null, dominioNoEncontradoException.getMessage())));
    }
}
