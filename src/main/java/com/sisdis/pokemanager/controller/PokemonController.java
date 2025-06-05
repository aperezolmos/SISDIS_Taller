package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.service.FlaskService;
import com.sisdis.pokemanager.service.PokemonService;
import com.sisdis.pokemanager.service.UserService;
import com.sisdis.pokemanager.service.FavoritePokemonService;
import com.sisdis.pokemanager.model.Pokemon;
import com.sisdis.pokemanager.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class PokemonController {

    @Autowired
    private FlaskService flaskService;

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private FavoritePokemonService favoritePokemonService;

    @Autowired
    private UserService userService;


    @GetMapping("/search-pokemon")
    public String buscarPokemon(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset,
            Model model,
            HttpSession session) {

        Map<String, Object> result = flaskService.searchPokemons(name, limit, offset);
        List<Map<String, Object>> pokemons = (List<Map<String, Object>>) result.get("results");
        int total = (int) result.get("total");

        model.addAttribute("pokemons", pokemons);
        model.addAttribute("limit", limit);
        model.addAttribute("offset", offset);
        model.addAttribute("total", total);
        model.addAttribute("name", name != null ? name : "");

        // Para saber si el usuario está logueado y su id
        User user = (User) session.getAttribute("user");
        model.addAttribute("userId", user != null ? user.getId() : null);

        return "search-pokemon";
    }

    @PostMapping("/add-favorite")
    public String agregarAFavoritos(
            @RequestParam String pokemonName,
            @RequestParam String imageUrl,
            @RequestParam(required = false) Integer externalId,
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset,
            Authentication authentication,
            Model model) {

        String username = authentication.getName();
        User user = userService.findUserEntityByUsername(username);
        
        // 1. Comprobar si el Pokémon ya existe en la BBDD
        Pokemon pokemon = pokemonService.findByName(pokemonName).stream().findFirst().orElse(null);
        if (pokemon == null) {
            // Si no existe, lo creamos
            pokemon = new Pokemon();
            pokemon.setName(pokemonName);
            pokemon.setImageUrl(imageUrl);
            pokemon.setExternalId(externalId);
            pokemonService.registerPokemon(pokemon);
        }
        // 2. Añadir a favoritos
        favoritePokemonService.addFavoritePokemon(user.getId(), pokemon.getId());

        // Redirigir con mensaje de éxito a la misma página de búsqueda (manteniendo los parámetros)
        return "redirect:/search-pokemon?name=" + (name != null ? name : "") + "&limit=" + limit + "&offset=" + offset + "&success=1";
    }
}