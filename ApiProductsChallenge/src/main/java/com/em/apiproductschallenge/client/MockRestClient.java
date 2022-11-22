package com.em.apiproductschallenge.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign interface for comunication with mock client
 *
 * @author moraleze
 * @version 1.0.0
 * @since 21/11/2022
 */
@FeignClient(name = "api-mock-client", url = "${rest.url.api-mock-client}")
public interface MockRestClient {

    @GetMapping(value = "/product/{productId}/similarids")
    Response getSimilarProductIds(@PathVariable(value = "productId") String productId);

    @GetMapping(value = "/product/{productId}")
    Response getProductById2(@PathVariable(value = "productId") String productId);
}
