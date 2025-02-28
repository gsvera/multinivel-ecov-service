package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.PayAffiliate;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
public class PayAffiliateDTO {
    public Long id;
    public String idUser;
    public double amount;
    public Timestamp createdDate;
    public String description;
    public int statusPay;
    public String paymentFile;
    public String payMethod;
    public String nameAffiliate;
    public String emailAffiliate;
    public String phoneNumber;
    public PayAffiliateDTO(PayAffiliate payAffiliate) {
        this.id = payAffiliate.getId();
        this.idUser = payAffiliate.getIdUser();
        this.amount = payAffiliate.getAmount();
        this.createdDate = payAffiliate.getCreatedDate();
        this.description = payAffiliate.getDescription();
        this.statusPay = payAffiliate.getStatusPay();
        this.paymentFile = payAffiliate.getPaymentFile();
        this.payMethod = payAffiliate.getPayMethod();
    }
}
