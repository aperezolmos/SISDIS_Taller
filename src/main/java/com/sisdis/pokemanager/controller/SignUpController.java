package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.model.User;
import com.sisdis.pokemanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping
    public String processSignup(@ModelAttribute("user") @Valid User user,
                                BindingResult result,
                                @RequestParam("repeatPassword") String repeatPassword,
                                Model model) {
        // Validaciones básicas
        if (!user.getPassword().equals(repeatPassword)) {
            model.addAttribute("passwordError", "Las contraseñas no coinciden");
            return "signup";
        }
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            model.addAttribute("registrationError", "El usuario ya existe"); // TODO
            return "signup";
        }
        // Redirige a login para que el usuario inicie sesión
        return "redirect:/login?registered";
    }
}
