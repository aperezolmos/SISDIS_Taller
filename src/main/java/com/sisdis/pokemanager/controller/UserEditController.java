package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.model.User;
import com.sisdis.pokemanager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/edit")
public class UserEditController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showEditForm(Authentication authentication, Model model) {
        String currentUsername = authentication.getName();
        User user = userService.findUserEntityByUsername(currentUsername);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping
    public String processEdit(@ModelAttribute("user") @Valid User formUser,
                              BindingResult result,
                              @RequestParam("repeatPassword") String repeatPassword,
                              Authentication authentication,
                              Model model) {
        String currentUsername = authentication.getName();

        // Validar que el username no exista (excepto si no lo ha cambiado)
        if (!formUser.getUsername().equals(currentUsername) &&
                userService.existsByUsername(formUser.getUsername())) {
            model.addAttribute("usernameError", "El nombre de usuario ya existe");
            return "user-edit";
        }

        // Validar contraseñas
        if (!formUser.getPassword().equals(repeatPassword)) {
            model.addAttribute("passwordError", "Las contraseñas no coinciden");
            return "user-edit";
        }

        // Actualizar usuario
        User user = userService.findUserEntityByUsername(currentUsername);
        user.setUsername(formUser.getUsername());
        user.setPassword(passwordEncoder.encode(formUser.getPassword()));
        user.setRole(formUser.getRole());
        userService.updateUser(user);

        // Opcional: cerrar sesión para que el usuario vuelva a iniciar sesión con el nuevo username
        return "redirect:/login?edited";
    }
}