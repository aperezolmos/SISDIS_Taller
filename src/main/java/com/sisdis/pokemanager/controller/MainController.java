package com.sisdis.pokemanager.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    
    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        return "main"; // Renderiza la página intermedia (main.html)
    }
}
