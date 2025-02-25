package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_status_buy")
public class StatusBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type_status")
    private String typeStatus;
    @Column(name = "name_status")
    private String nameStatus;
    @Column(name = "order_status")
    private int orderStatus;
}
