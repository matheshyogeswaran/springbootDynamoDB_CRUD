package com.product.crud.model;

import lombok.Data;

@Data
public class ProductDTO {

    private Long productId;
    private String name;
    private double price;

    // Other attributes if needed

}
