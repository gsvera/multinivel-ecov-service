package com.ecov.multinivel.dto.generics;

import com.ecov.multinivel.entity.ResetPasswordToken;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResetPasswordTokenDTO {
    public Long id;
    public String token;
    public boolean active;
    public Timestamp createdDate;
    public String email;
    public ResetPasswordTokenDTO(ResetPasswordToken resetPasswordToken) {
        this.id = resetPasswordToken.getId();
        this.token = resetPasswordToken.getToken();
        this.active = resetPasswordToken.isActive();
        this.createdDate = resetPasswordToken.getCreatedDate();
        this.email = resetPasswordToken.getEmail();
    }
}
