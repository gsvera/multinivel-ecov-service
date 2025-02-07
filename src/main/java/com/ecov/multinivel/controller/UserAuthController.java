package com.ecov.multinivel.controller;


import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ecov/auth/user")
public class UserAuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/logout")
    public ResponseDTO logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        ResponseDTO responseDTO = new ResponseDTO();
        try{
            return userService._Logout(token.substring(7));
        } catch (Exception ex) {
            responseDTO.error = true;
            responseDTO.message = ex.getMessage();
//            response.message = "Ocurrio un error intentelo mas tarde";
        }
        return  responseDTO;
    }
}
