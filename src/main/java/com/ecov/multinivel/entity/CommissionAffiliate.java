package com.ecov.multinivel.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private Long idPay;
    @Column(name = "status_pay")
    private int statusPay;
    @ManyToOne
    @JoinColumn(name = "id_user_commission", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonManagedReference("commission-user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_pay", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonManagedReference("commission-payAffiliate")
    private PayAffiliate payAffiliate;
}
