package com.sisdis.pokemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private FlaskService flaskService;


    public boolean validateUser(String username, String password) {
        return flaskService.validateUser(username, password);
    }
}
