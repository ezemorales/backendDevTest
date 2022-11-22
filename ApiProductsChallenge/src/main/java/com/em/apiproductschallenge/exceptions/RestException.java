package com.em.apiproductschallenge.exceptions;

public class RestException extends RuntimeException {
    private static final long serialVersionUID = 446216167420436240L;

    public RestException(String mensaje) {
        super(mensaje);
    }
}
