package com.em.apiproductschallenge.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Class that contains the messages of the project as constants
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
    /**
     * REST ERROR
     */
    public static final String ERROR_CONVERSION = "The response could not be converted {}";
    public static final String ERROR_COMUNICATION = "Comunication error with %s";
    public static final String ERROR_TIMEOUT_SERVER = "Timeout error with server %s";
    /**
     * DOMAIN NOT FOUND
     **/
    public static final String PRODUCT_NOT_FOUND = "Product not found";
}
