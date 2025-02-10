package com.ecov.multinivel.service;

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
import org.springframework.beans.factory.annotation.Value;
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
    private final ResetTokenService resetTokenService;
    private final MailService mailService;
    @Value("${url.front}")
    public String urlFront;

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

        if(user.isPresent()) {
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
            String htmlBody = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Reset Password Eco-v</title>"+
                    "    <style>\n" +
                    "       span, strong, h1, div{\n" +
                    "            font-family: sans-serif;\n" +
                    "        }\n" +
                    "        .card-ecov {\n" +
                    "            padding: 50px;\n" +
                    "            border-radius: 15px;\n" +
                    "            box-shadow: 0 0 0 0 rgba(0, 0, 0, 0.5);\n" +
                    "            margin: auto;\n" +
                    "            color: gray;\n" +
                    "            font-weight: bold;\n" +
                    "            width: 350px;\n" +
                    "            text-align: justify;\n" +
                    "            background-color: white;\n" +
                    "        }\n" +
                    "        .link-ecov {\n" +
                    "            color: gray;\n" +
                    "        }"+
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div style=\"background-color: #182236; padding: 50px;\" >\n" +
                    "        <div class=\"card-ecov\">\n" +
                    "            <p>Hola "+user.get().getFirstName()+" "+user.get().getLastName()+", se solicto un cambio de contraseña, a continuacion ingrese al siguiente link para cambiar su contraseña, si usted no realizo esta solicitud favor de ignorar este mensaje.</p>\n" +
                    "           <br />\n" +
                    "            <br />\n" +
                    "           <div style=\"text-align: center;\">\n" +
                    "                <a class=\"link-ecov\" href=\""+urlFront+"/reset-new-password?token="+token+"\">De click aqui</a>\n" +
                    "            </div>"+
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>";
            mailService.SendEmail(user.get().getEmail(), "Eco-v recuperación de contraseña", htmlBody);

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
}
