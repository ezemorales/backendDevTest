package com.em.apiproductschallenge.advice;

import com.em.apiproductschallenge.exceptions.OpenFeignException;
import com.em.apiproductschallenge.utils.errors.ApiError;
import com.em.apiproductschallenge.utils.errors.ApiFieldError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;

@ControllerAdvice
public class RestAdvice {

    @ResponseBody
    @ExceptionHandler(OpenFeignException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError feignTimeOutException(OpenFeignException openFeignException){
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                Collections.singletonList(new ApiFieldError(null, openFeignException.getMessage())));
    }


}
