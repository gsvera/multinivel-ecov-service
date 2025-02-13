package com.ecov.multinivel.service;

import com.ecov.multinivel.config.ConstantsConfig;
import com.ecov.multinivel.dto.ReferenceAffiliateDTO;
import com.ecov.multinivel.dto.generics.LoginRequestDTO;
import com.ecov.multinivel.dto.generics.ResetPasswordTokenDTO;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.dto.generics.ChangePassword;
import com.ecov.multinivel.dto.generics.SessionDTO;
import com.ecov.multinivel.entity.User;
import com.ecov.multinivel.repository.UserRepository;
import com.ecov.multinivel.utils.EncrypDecrypCode;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ResetTokenService resetTokenService;
    private final MailService mailService;
    private final ReferenceAffiliateService referenceAffiliateService;
    private final ConstantsConfig constantsConfig;

    public ResponseDTO _Save(UserDTO userDTO, String reference) {
        Optional<User> user = userRepository.findByEmail(userDTO.email);
        ReferenceAffiliateDTO referenceAffiliateDTO = referenceAffiliateService._GetByReference(reference.toUpperCase());

        if(user.isPresent()) {
            return ResponseDTO.builder().error(true).message("Ya existe un usuario con esos datos").build();
        }
        if(referenceAffiliateDTO == null) {
            return ResponseDTO.builder().error(true).message("No se pudo crear el usuario, debe agregar una referencia valida").build();
        }

        UUID uuid = UUID.randomUUID();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setId(uuid.toString());
        userDTO.setCreatedDate(Timestamp.from(Instant.now()));
        userDTO.setReferenceParent(referenceAffiliateDTO.idUser);
        try{
            String decryptPass = EncrypDecrypCode.passwordDecrypt(userDTO.password); // DESENCRYPTAR PASSWORD DE CLIENT FRONT
            String encodedPassword = encoder.encode(decryptPass); // ENCRYPTAR PARA GUARDAR EN BD
            userDTO.setPassword(encodedPassword);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }

        User newUser = new User(userDTO);

        userRepository.save(newUser);
        referenceAffiliateService._SaveReferenceByUser(userDTO.getFirstName().substring(0, 2).toUpperCase() + userDTO.getLastName().substring(0,2).toUpperCase(), userDTO.getId());

        String token = jwtService.GetToken(newUser);
        userRepository.updateTokenById(newUser.getId(), token);

        try{
            if(userDTO.getWorkgroupId() == constantsConfig.getWorkGroupIdAffiliate()) {
                String completeName = userDTO.getFirstName() +" "+userDTO.getLastName();
                mailService._SendNewAffiliate(completeName, userDTO.getEmail(), userDTO.getId());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        return ResponseDTO.builder().items(
                    SessionDTO.builder()
                            .token(token)
                            .userDTO(userDTO).build()
                    ).build();
    }
    public ResponseDTO _Login(LoginRequestDTO loginRequestDTO) throws Exception {
        Optional<User> user = userRepository.findByEmail(loginRequestDTO.email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String decryptPass = EncrypDecrypCode.passwordDecrypt(loginRequestDTO.password);

        if(user.isPresent()) {
            if(!user.get().isActive()) {
                return ResponseDTO.builder().error(true).message("Su cuenta no ha sido verificada o esta inactiva").build();
            }
            boolean passwordMatch = encoder.matches(decryptPass, user.get().getPassword());
            if(passwordMatch) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.email, decryptPass));
                String token = jwtService.GetToken(user.get());
                userRepository.updateTokenById(user.get().getId(), token);
                SessionDTO sessionDTO = new SessionDTO();
                sessionDTO.token = token;
                sessionDTO.userDTO = new UserDTO(user.get());
                // SI CREAMOS VARIOS PERFILES Y QUE CADA PERFIL TENGA PERMISOS AQUI DEBE IR
                return ResponseDTO.builder().items(sessionDTO).build();
            }
        }
        return ResponseDTO.builder().error(true).message("Usuario y/o contraseña incorrecto").build();
    }
    public ResponseDTO _Logout(String token) {
        userRepository.updateToken(token);
        return ResponseDTO.builder().build();
    }
    public ResponseDTO _SendRecoveryPassword(String email) throws MessagingException {
        ResponseDTO responseDTO = new ResponseDTO();
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            String token = resetTokenService._GenerateToken(email);
            String completeName = user.get().getFirstName()+" "+user.get().getLastName();
            mailService._SendRecoveryPassword(completeName, user.get().getEmail(), token);

        } else {
            responseDTO.error = true;
        }
        return responseDTO;
    }
    public ResponseDTO _ValidExpiredTokenRecoveryPassword(String token) {
        ResetPasswordTokenDTO resetPasswordToken = resetTokenService._GetToken(token);
        if(resetPasswordToken != null) {
            boolean tokenValid = resetTokenService._IsValidToken(resetPasswordToken);
            if(tokenValid) {
                return ResponseDTO.builder().build();
            }
        }
        return ResponseDTO.builder().error(true).message("El token ha expirado").build();
    }
    public ResponseDTO _UpdateNewPasswordByToken(ChangePassword changePassword) throws Exception {
        ResetPasswordTokenDTO resetPasswordTokenDTO = resetTokenService._GetToken(changePassword.token);

        if(resetPasswordTokenDTO != null) {
            Optional<User> user = userRepository.findByEmail(resetPasswordTokenDTO.getEmail());
            if(user.isPresent()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String decryptPass = EncrypDecrypCode.passwordDecrypt(changePassword.newPassword);
                String encodedPassword = encoder.encode(decryptPass);
                user.get().setPassword(encodedPassword);
                userRepository.save(user.get());
                resetTokenService._DeleteTokenById(resetPasswordTokenDTO.getId());
                return ResponseDTO.builder().message("Contraseña actualizada con éxito").build();
            }
        }
        return ResponseDTO.builder().error(true).message("El token ha expirado").build();
    }
    public ResponseDTO _ConfirmAccount(String tokenConfirm) {
        Optional<User> user = userRepository.findById(tokenConfirm);
        if(user.isPresent()) {
            user.get().setActive(true);
            userRepository.save(user.get());
            return ResponseDTO.builder().message("Cuenta confirmada").build();
        }
        return ResponseDTO.builder().error(true).message("No se ha encontrado una cuenta para confirmar").build();
    }
}
