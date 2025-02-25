package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.DetailPayProduct;

public class DetailPayProductDTO {
    public Long id;
    public Long idProduct;
    public int firstPay;
    public int lastPay;
    public double amount;
    public DetailPayProductDTO(DetailPayProduct detailPayProduct) {
        this.id = detailPayProduct.getId();
        this.idProduct = detailPayProduct.getIdProduct();
        this.firstPay = detailPayProduct.getFirstPay();
        this.lastPay = detailPayProduct.getLastPay();
        this.amount = detailPayProduct.getAmount();
    }
}
