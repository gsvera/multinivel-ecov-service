package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    public Boolean active;
    public Timestamp createdDate;
    private String referenceParent;
    private int nivel;
    private List<UserDTO> child = new ArrayList<>();
    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.workgroupId = user.getWorkgroupId();
        this.phoneNumber = user.getPhoneNumber();
        this.referenceParent = user.getReferenceParent();
        this.active = user.getActive();
    }
    public UserDTO(String id, String firstName, String lastName, int nivel, Timestamp createdDate, Boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nivel = nivel;
        this.createdDate = createdDate;
        this.active = active;
    }
}
