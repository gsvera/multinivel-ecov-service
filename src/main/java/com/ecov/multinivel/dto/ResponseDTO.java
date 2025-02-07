package com.ecov.multinivel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDTO {
    public boolean error;
    public String message;
    public Object items;
    public ResponseDTO(Object obj) {
        this.items = obj;
    }
}