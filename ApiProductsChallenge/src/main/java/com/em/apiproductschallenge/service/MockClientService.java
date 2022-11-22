package com.em.apiproductschallenge.service;

import com.em.apiproductschallenge.model.ProductDetail;

import java.util.List;

/**
 * Interface for communication with api-mock-client
 */
public interface MockClientService {
    ProductDetail getProductById(String productId);

    List<ProductDetail> getSimilarProducts(String productId);

}
