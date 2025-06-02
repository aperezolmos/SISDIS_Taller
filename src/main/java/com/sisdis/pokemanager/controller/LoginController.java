package com.sisdis.pokemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String showLoginForm() {
        return "login"; // Renderiza el formulario de login (login.html)
    }
}
