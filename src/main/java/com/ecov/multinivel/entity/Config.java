package com.ecov.multinivel.entity;

import com.ecov.multinivel.dto.ConfigDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tbl_config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "label_descript")
    private String labelDescript;
    @Column(name = "value_number")
    private int valueNumber;
    @Column(name = "value_string")
    private String valueString;
    @Column(name = "value_boolean")
    private Boolean valueBoolean;
    public Config(ConfigDTO configDTO) {
        this.id = configDTO.id;
        this.labelDescript = configDTO.labelDescript;
        this.valueNumber = configDTO.valueNumber;
        this.valueString = configDTO.valueString;
        this.valueBoolean = configDTO.valueBoolean;
    }
}
