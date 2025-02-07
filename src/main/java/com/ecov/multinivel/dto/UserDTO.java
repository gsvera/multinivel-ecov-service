package com.ecov.multinivel.dto;

import com.ecov.multinivel.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.token = user.getToken();
    }
}
