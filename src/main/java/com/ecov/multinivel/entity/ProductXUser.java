package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tbl_product_x_user")
public class ProductXUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user")
    private String idUser;
    @Column(name = "id_product")
    private Long idProduct;
    @Column(name = "date_buy")
    private Timestamp dateBuy;
    @Column(name = "id_status_buy")
    private Long idStatusBuy;
    @Column(name = "pay_method")
    private String payMethod;
    @Column(name = "payment_file", columnDefinition = "text")
    private String paymentFile;
}
