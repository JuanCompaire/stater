package com.example.stater.controller;

import java.util.HashMap;
import java.util.Map;

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
@RequestMapping("/api/auth") // Endpoint
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {
        Boolean result = service.login(loginRequest.getPassword());
        System.out.println("El result del Controller es : " + result);

        if (result) {
            // Si el login es exitoso, devolvemos un mensaje de éxito
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login realizado correctamente");
            return ResponseEntity.ok(response);
        } else {
            // Si el login falla, devolvemos un mensaje de error
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error en el login");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
}
