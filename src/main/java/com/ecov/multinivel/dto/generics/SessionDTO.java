package com.ecov.multinivel.dto.generics;

import com.ecov.multinivel.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {
    public String token;
    public UserDTO userDTO;
}
