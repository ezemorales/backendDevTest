package com.em.apiproductschallenge.utils.errors;

import com.em.apiproductschallenge.exceptions.DomainNotFoundException;
import com.em.apiproductschallenge.exceptions.RestException;
import com.em.apiproductschallenge.utils.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/**
 * Class for analyze errors and throw exceptions
 * @author moraleze
 * @since 07/08/2022
 * @version 1.0.0
 */
@Slf4j
public class ErrorDecoder {

    public static void decode(Response response, String server) {
        // client errors
        if (response.status() >= HttpStatus.BAD_REQUEST.value()
                && response.status() <= HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS.value()) {
            processErrorClient(response, server);
        }

        // server errors
        if (response.status() >= HttpStatus.INTERNAL_SERVER_ERROR.value() && response.status() <= 599) {
            throw new RestException("Server " + server + " not responding");
        }
    }

    private static void processErrorClient(Response response, String server) {

        //NOT FOUND
        if (HttpStatus.NOT_FOUND.value() == response.status()) {
            log.warn("API RESPONSE: {}", Messages.PRODUCT_NOT_FOUND);
            throw new DomainNotFoundException(Messages.PRODUCT_NOT_FOUND);
        }
        //GENERIC
        log.error("Error - request url {}, error code {}, reason {}", response.request().url(),
                response.status(), response.reason());
        throw new RestException("Error with server " + server);
    }

}
