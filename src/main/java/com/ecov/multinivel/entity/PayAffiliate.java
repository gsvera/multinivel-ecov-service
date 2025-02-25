package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "tbl_pay_affiliate")
public class PayAffiliate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user")
    private String idUser;
    private double amount;
    @Column(name = "created_date")
    private Timestamp createdDate;
    private String description;
    @Column(name = "status_pay")
    private int statusPay;
    @Column(name = "payment_file", columnDefinition = "text")
    private String paymentFile;
    @Column(name = "pay_method")
    private String payMethod;

}
