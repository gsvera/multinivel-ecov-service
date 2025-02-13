package com.ecov.multinivel.controller;

import com.ecov.multinivel.config.ConstantsConfig;
import com.ecov.multinivel.dto.generics.LoginRequestDTO;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.dto.generics.ChangePassword;
import com.ecov.multinivel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecov/user/public")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ConstantsConfig constantsConfig;
//    @PostMapping("/save")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseDTO saveUser(@RequestBody UserDTO userDTO){
//        ResponseDTO responseDTO = new ResponseDTO();
//        try{
//            return userService._Save(userDTO);
//        } catch (Exception ex) {
//            responseDTO.error = true;
////            responseDTO.message = ex.getMessage();
//            responseDTO.message = "Ocurrio un error intentelo mas tarde";
//        }
//        return responseDTO;
//    }
    @PostMapping("/new-affiliate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO newAffiliate(@RequestParam String reference, @RequestBody UserDTO userDTO) {
        try{
            userDTO.setWorkgroupId(constantsConfig.getWorkGroupIdAffiliate());
            return userService._Save(userDTO, reference);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return  ResponseDTO.builder().error(true).message("Ocurrio un error intentelo mas tarde").build();
        }
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            return userService._Login(loginRequestDTO);
        } catch(Exception ex) {
            responseDTO.error = true;
            responseDTO.message = "Usuario y/o contraseña incorrecto";
            System.out.println(ex.getMessage());
        }
        return responseDTO;
    }

    @PostMapping("/recovery-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO recoveryPassword(@RequestParam String email) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            return userService._SendRecoveryPassword(email);
        } catch (Exception ex) {
            responseDTO.error = true;
            System.out.println(ex.getMessage());
            responseDTO.message = "Ocurrio un error intentelo mas tarde";
        }
        return  responseDTO;
    }
    @GetMapping("/valid-expired-token-recovery-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO validExpiredTokenRecoveryPassword(@RequestParam String token) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            return userService._ValidExpiredTokenRecoveryPassword(token);
        } catch (Exception ex) {
            responseDTO.error = true;
            responseDTO.message = "Ocurrio un error intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return responseDTO;
    }
    @PutMapping("/save-new-password")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO saveNewPassword(@RequestBody ChangePassword changePassword) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            return userService._UpdateNewPasswordByToken(changePassword);
        } catch (Exception ex) {
            responseDTO.error = true;
            responseDTO.message = "Ocurrio un error intentelo mas tarde";
            System.out.println(ex.getMessage());
        }
        return responseDTO;
    }
    @PutMapping("/confirm-account")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO confirmAccount(@RequestParam(name = "token-confirm") String tokenConfirm) {
        try{
            return userService._ConfirmAccount(tokenConfirm);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return  ResponseDTO.builder().error(true).message(ex.getMessage()).build();
        }
    }
}
