package com.ecov.multinivel.service;

import com.ecov.multinivel.dto.LoginRequestDTO;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.entity.User;
import com.ecov.multinivel.repository.UserRepository;
import com.ecov.multinivel.utils.EncrypDecrypCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseDTO _Save(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmail(userDTO.email);

        if(user.isPresent()) {
            return ResponseDTO.builder().error(true).message("Ya existe un usuario con esos datos").build();
        }

        UUID uuid = UUID.randomUUID();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userDTO.password); // EL PASSWORD DEBE VENIR ENCRYPTADO DESDE FRONT PARA DESENCRYPTAR PARA SEGURIDAD DE COMUNICACION
        userDTO.setId(uuid.toString());
        userDTO.setPassword(encodedPassword);

        User newUser = new User(userDTO);

        userRepository.save(newUser);

        String token = jwtService.GetToken(newUser);
        userRepository.updateTokenById(newUser.getId(), token);

        return ResponseDTO.builder().items(token).build();
    }
    public ResponseDTO _Login(LoginRequestDTO loginRequestDTO) throws Exception {
        Optional<User> user = userRepository.findByEmail(loginRequestDTO.email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String decryptPass = EncrypDecrypCode.passwordDecrypt(loginRequestDTO.password);
        System.out.println("desencryptado " + decryptPass);
        if(user.isPresent()) {
            boolean passwordMatch = encoder.matches(decryptPass, user.get().getPassword());
            if(passwordMatch) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email, decryptPass));
                String token = jwtService.GetToken(user.get());
                int saveToken = userRepository.updateTokenById(user.get().getId(), token);
                // SI CREAMOS VARIOS PERFILES Y QUE CADA PERFIL TENGA PERMISOS AQUI DEBE IR
                return ResponseDTO.builder().items(token).build();
            }
        }
        return ResponseDTO.builder().error(true).message("Usuario y/o contraseña incorrecto").build();
    }
    public ResponseDTO _Logout(String token) {
        userRepository.updateToken(token);
        return ResponseDTO.builder().build();
    }
}
