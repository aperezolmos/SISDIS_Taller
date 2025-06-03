package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.service.FlaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class PokeListController {

    @Autowired
    private FlaskService flaskService;

    
    @GetMapping("/list")
    public String listPokemons(
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset,
            Model model,
            HttpSession session) {
        
        try {
            List<Map<String, String>> pokemons = flaskService.listPokemons(limit, offset);
            model.addAttribute("pokemons", pokemons);
            model.addAttribute("limit", limit);
            
        } catch (Exception e) {
            model.addAttribute("error", "Error al obtener la lista de Pokémon: " + e.getMessage());
        }

        return "pokeapi-calls";
    }
}
