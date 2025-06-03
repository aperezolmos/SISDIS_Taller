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
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Controller
@RequestMapping("/user/edit")
public class UserEditController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showEditForm(@RequestParam(value = "username", required = false) String usernameParam,
                               Authentication authentication,
                               Model model) {
        String usernameToEdit = (usernameParam != null) ? usernameParam : authentication.getName();
        User user = userService.findUserEntityByUsername(usernameToEdit);
        model.addAttribute("user", user);
        model.addAttribute("editingUsername", usernameToEdit);
        // ¿El usuario autenticado es admin?
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_admin"));
        model.addAttribute("isAdmin", isAdmin);
        return "user-edit";
    }

    @PostMapping
    public String processEdit(@ModelAttribute("user") @Valid User formUser,
                              BindingResult result,
                              @RequestParam("repeatPassword") String repeatPassword,
                              @RequestParam(value = "editingUsername") String editingUsername,
                              Authentication authentication,
                              Model model) {
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_admin"));
        User userToEdit = userService.findUserEntityByUsername(editingUsername);

        // Validar que el username no exista (excepto si no lo ha cambiado)
        if (!formUser.getUsername().equals(editingUsername) &&
                userService.existsByUsername(formUser.getUsername())) {
            model.addAttribute("usernameError", "El nombre de usuario ya existe");
            model.addAttribute("editingUsername", editingUsername);
            model.addAttribute("isAdmin", isAdmin);
            return "user-edit";
        }

        // Validar contraseñas
        if (!formUser.getPassword().equals(repeatPassword)) {
            model.addAttribute("passwordError", "Las contraseñas no coinciden");
            model.addAttribute("editingUsername", editingUsername);
            model.addAttribute("isAdmin", isAdmin);
            return "user-edit";
        }

        // Actualizar usuario
        userToEdit.setUsername(formUser.getUsername());
        userToEdit.setPassword(passwordEncoder.encode(formUser.getPassword()));
        // Solo admin puede cambiar el rol
        if (isAdmin) {
            userToEdit.setRole(formUser.getRole());
        }
        userService.updateUser(userToEdit);

        // Si el usuario autenticado se ha editado a sí mismo, cerrar sesión para que vuelva a loguear
        if (editingUsername.equals(authentication.getName())) {
            return "redirect:/login?edited";
        } else {
            return "redirect:/user/management?edited";
        }
    }
}
