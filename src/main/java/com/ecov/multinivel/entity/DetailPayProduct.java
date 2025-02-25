package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_detail_pay_product")
public class DetailPayProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_product")
    private Long idProduct;
    @Column(name = "first_pay")
    private int firstPay;
    @Column(name = "last_pay")
    private int lastPay;
    private double amount;
}
