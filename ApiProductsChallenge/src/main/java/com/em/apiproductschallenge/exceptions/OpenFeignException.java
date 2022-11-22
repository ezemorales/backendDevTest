package com.em.apiproductschallenge.exceptions;

import com.em.apiproductschallenge.utils.Messages;

public class OpenFeignException extends RuntimeException {
    private static final long serialVersionUID = 6472897246677800628L;

    public OpenFeignException(String message, Throwable cause) {
        super(String.format(Messages.ERROR_TIMEOUT_SERVER, message), cause);
    }
}
