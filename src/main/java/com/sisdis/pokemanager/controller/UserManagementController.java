package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.dto.UserDTO;
import com.sisdis.pokemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/user/management")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showUserManagement(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-management";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id, Authentication authentication) {
        // Evitar que un admin se borre a sí mismo
        UserDTO userToDelete = userService.findById(id);
        if (userToDelete.getUsername().equals(authentication.getName())) {
            return "redirect:/user/management?error=cannot_delete_self";
        }
        userService.deleteById(id);
        return "redirect:/user/management?deleted";
    }
}
