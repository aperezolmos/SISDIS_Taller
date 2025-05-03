package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    
    @GetMapping
    public String showLoginForm() {
        return "login"; // Renderiza el formulario de login (login.html)
    }

    @PostMapping
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        boolean isValid = loginService.validateUser(username, password);

        if (isValid) {
            // Guarda el nombre del usuario en la sesión
            session.setAttribute("currentUser", username);
            return "redirect:/main"; // Redirige a la página principal
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login"; // Muestra el formulario con un mensaje de error
        }
    }
}
