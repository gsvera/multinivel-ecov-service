package com.ecov.multinivel.controller;

import com.ecov.multinivel.dto.LoginRequestDTO;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ecov/user/public")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO saveUser(@RequestBody UserDTO userDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            return userService._Save(userDTO);
        } catch (Exception ex) {
            responseDTO.error = true;
//            responseDTO.message = ex.getMessage();
            responseDTO.message = "Ocurrio un error intentelo mas tarde";
        }
        return responseDTO;
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
//            responseDTO.message = ex.getMessage();
        }
        return responseDTO;
    }
}
