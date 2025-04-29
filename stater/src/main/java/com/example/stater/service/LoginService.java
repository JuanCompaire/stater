package com.example.stater.service;

import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public boolean login(String password){
        System.out.println("Passowrd : "+password);
        if(password.equals("SZKlolito")){
            return true;
        }
        else{
            System.out.println("False en el LoginService");
            return false;
        }
    }
}
