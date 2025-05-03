package com.sisdis.pokemanager.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimulateController {

    @GetMapping("/simulate")
    public String showSimulationPage(Model model, HttpSession session) {
        // Obtiene el nombre del usuario de la sesión
        String username = (String) session.getAttribute("currentUser");
        model.addAttribute("username", username); // Añade el nombre de usuario al modelo
        return "simulate"; // Renderiza la página de simulación (simulate.html)
    }
}
