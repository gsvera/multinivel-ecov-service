package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name =  "tbl_reset_password_token")
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private boolean active;
    @Column(name = "created_date")
    private Timestamp createdDate;
    private String email;
}
