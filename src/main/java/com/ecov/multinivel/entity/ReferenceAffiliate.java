package com.ecov.multinivel.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tbl_reference_affiliate")
public class ReferenceAffiliate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_reference")
    private String codeReference;
    @Column(name = "id_user")
    private String idUser;
    public ReferenceAffiliate (String codeReference, String idUser) {
        this.codeReference = codeReference;
        this.idUser = idUser;
    }
}
