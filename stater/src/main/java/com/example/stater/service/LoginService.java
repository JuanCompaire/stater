package com.example.stater.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean login(String password) {
        System.out.println("Password : " + password);
        // Lógica de validación de contraseña
        if ("SZKlolito".equals(password)) { // Contraseña correcta
            return true;
        } else {
            // Contraseña incorrecta
            System.out.println("Contraseña incorrecta");
            return false;
        }
    }
}
