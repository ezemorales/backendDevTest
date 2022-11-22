package com.em.apiproductschallenge.exceptions;

public class DomainNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -2176780934196844927L;
    public DomainNotFoundException(String mensaje) {
        super(mensaje);
    }
}
