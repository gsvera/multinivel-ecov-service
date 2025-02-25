package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.Product;

public class ProductDTO {
    public Long id;
    public String name;
    public double price;
    public String image;

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.image = product.getImage();
    }
}
