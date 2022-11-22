package com.em.apiproductschallenge.controller;

import com.em.apiproductschallenge.model.ProductDetail;
import com.em.apiproductschallenge.service.MockClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Product Controller
 *
 * @author moraleze
 * @version 1.0.0
 * @since 21/11/2022
 */
@RestController
public class ProductController {

    @Autowired
    private MockClientService mockClientService;

    @GetMapping("/product/{productId}/similar")
    public ResponseEntity<List<ProductDetail>> getSimilarProducts(@PathVariable(value = "productId") String productId) {
        return ResponseEntity.ok(mockClientService.getSimilarProducts(productId));
    }

}
