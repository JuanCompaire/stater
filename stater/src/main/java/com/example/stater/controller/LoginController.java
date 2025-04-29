package com.example.stater.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stater.DTO.LoginRequest;
import com.example.stater.service.LoginService;

@RestController
@RequestMapping("/api/auth")//EndPoint 
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping("/login")//EndPoint --> /api/auth/login
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){

        Boolean result = service.login(loginRequest.getPassword());
        System.out.println("El result del Controler es : "+result);
        if (result) {
        // Si el login es exitoso, devuelve un código 200 con un mensaje
        return ResponseEntity.ok("Login realizado correctamente");
        } else {
            // Si el login falla, devuelve un código 401 con un mensaje de error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error en el login");
        }
    }
}
