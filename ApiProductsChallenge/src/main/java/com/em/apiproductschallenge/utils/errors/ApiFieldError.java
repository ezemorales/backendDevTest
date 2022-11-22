package com.em.apiproductschallenge.utils.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class ApiFieldError {

    private String field;
    private String message;

    public ApiFieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
