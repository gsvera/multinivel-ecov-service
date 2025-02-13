package com.ecov.multinivel.dto;


import com.ecov.multinivel.entity.Config;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
public class ConfigDTO {
    public Long id;
    public String labelDescript;
    public int valueNumber;
    public String valueString;
    public Boolean valueBoolean;
    public ConfigDTO(Config config) {
        this.id = config.getId();
        this.labelDescript = config.getLabelDescript();
        this.valueNumber = config.getValueNumber();
        this.valueString = config.getValueString();
        this.valueBoolean = config.getValueBoolean();
    }
}
