package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_commission_affiliate")
public class CommissionAffiliate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_user_pay")
    private String idUserPay;
    @Column(name = "id_user_commission")
    private String idUserCommission;
    @Column(name = "amount_commission")
    private double amountCommission;
    @Column(name = "id_pay")
    private double idPay;
    @Column(name = "status_pay")
    private int statusPay;
}
