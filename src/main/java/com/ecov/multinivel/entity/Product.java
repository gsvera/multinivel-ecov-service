package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_product")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private String image;
    @Column(name = "first_commission")
    private double firstCommission;
    @Column(name = "after_commission")
    private double afterCommission;
}
