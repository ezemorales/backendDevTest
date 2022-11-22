package com.em.apiproductschallenge.model;

import lombok.Data;

/**
 * Model of product detail
 */
@Data
public class ProductDetail {
    private String id;
    private String name;
    private double price;
    private boolean availability;
}
