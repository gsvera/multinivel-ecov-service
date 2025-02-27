package com.ecov.multinivel.dto;

import java.sql.Timestamp;

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
}
