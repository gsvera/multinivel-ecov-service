package com.ecov.multinivel.entity;

import com.ecov.multinivel.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_users")
public class User implements UserDetails {
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private String token;
    @Column(name = "workgroup_id")
    private int workgroupId;
    @Column(name = "phone_number")
    private String phoneNumber;
    private Boolean active;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "reference_parent")
    private String referenceParent;
    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.token = userDTO.getToken();
        this.workgroupId = userDTO.getWorkgroupId();
        this.phoneNumber = userDTO.getPhoneNumber();
        this.createdDate = userDTO.getCreatedDate();
        this.referenceParent = userDTO.getReferenceParent();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // AQUI ENTRARIA LA LOGICA POR PERFILES (ROLES)
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
