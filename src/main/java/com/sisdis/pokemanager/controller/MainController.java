package com.sisdis.pokemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sisdis.pokemanager.model.User;
import com.sisdis.pokemanager.model.FavoritePokemon;
import com.sisdis.pokemanager.model.Pokemon;
import com.sisdis.pokemanager.service.UserService;
import com.sisdis.pokemanager.service.DailyPokemonService;
import com.sisdis.pokemanager.service.FavoritePokemonService;
import com.sisdis.pokemanager.service.FlaskService;
import com.sisdis.pokemanager.service.PokemonService;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private FavoritePokemonService favoritePokemonService;

    @Autowired
    private DailyPokemonService dailyPokemonService;

    @Autowired
    private FlaskService flaskService;

    @Autowired
    private PokemonService pokemonService;


    @GetMapping("/main")
    public String showMainPage(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findUserEntityByUsername(username);

        List<FavoritePokemon> favorites = user.getFavoritePokemons();
        model.addAttribute("user", user);
        model.addAttribute("favorites", favorites);

        // Obtener Pokémon diario
        int dailyId = dailyPokemonService.getDailyPokemonId();
        Map<String, Object> dailyPokemon = flaskService.getDailyPokemon(dailyId);
        model.addAttribute("dailyPokemon", dailyPokemon);

        return "main";
    }

    @PostMapping("/remove-favorite")
    public String removeFavorite(@RequestParam Long favoriteId) {
        favoritePokemonService.removeFavoritePokemon(favoriteId);
        return "redirect:/main";
    }

    @PostMapping("/add-favorite-main")
    public String addFavoriteFromMain(
            @RequestParam String pokemonName,
            @RequestParam String imageUrl,
            @RequestParam(required = false) Integer externalId,
            Authentication authentication
    ) {
        String username = authentication.getName();
        User user = userService.findUserEntityByUsername(username);

        // Buscar o crear el Pokémon
        Pokemon pokemon = pokemonService.findByName(pokemonName).stream().findFirst().orElse(null);
        if (pokemon == null) {
            pokemon = new Pokemon();
            pokemon.setName(pokemonName);
            pokemon.setImageUrl(imageUrl);
            pokemon.setExternalId(externalId);
            pokemonService.registerPokemon(pokemon);
        }
        favoritePokemonService.addFavoritePokemon(user.getId(), pokemon.getId());
        return "redirect:/main";
    }
}
