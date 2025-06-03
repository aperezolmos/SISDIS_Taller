package com.sisdis.pokemanager.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimulateController {

    @GetMapping("/simulate")
    public String showSimulationPage(Model model, HttpSession session) {
        return "simulate"; // Renderiza la página de simulación (simulate.html)
    }
}
