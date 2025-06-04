package com.sisdis.pokemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sisdis.pokemanager.model.User;
import com.sisdis.pokemanager.model.FavoritePokemon;
import com.sisdis.pokemanager.service.UserService;
import com.sisdis.pokemanager.service.FavoritePokemonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoritePokemonService favoritePokemonService;

    @GetMapping("/main")
    public String showMainPage(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findUserEntityByUsername(username);

        // Obtener favoritos del usuario
        List<FavoritePokemon> favorites = user.getFavoritePokemons();

        model.addAttribute("user", user);
        model.addAttribute("favorites", favorites);

        return "main";
    }

    @PostMapping("/remove-favorite")
    public String removeFavorite(@RequestParam Long favoriteId) {
        favoritePokemonService.removeFavoritePokemon(favoriteId);
        return "redirect:/main";
    }
}
