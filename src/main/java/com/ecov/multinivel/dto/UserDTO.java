package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String token;
    public int workgroupId;
    public String phoneNumber;
    public Timestamp createdDate;
    private String referenceParent;
    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.workgroupId = user.getWorkgroupId();
        this.phoneNumber = user.getPhoneNumber();
        this.referenceParent = user.getReferenceParent();
    }
}
