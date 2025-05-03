package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.dto.FavoritePokemonDTO;
import com.sisdis.pokemanager.service.FavoritePokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoritePokemonController {

    @Autowired
    private FavoritePokemonService favoritePokemonService;

    
    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoritePokemonDTO>> getFavoritesByUser(@PathVariable Long userId) {
        List<FavoritePokemonDTO> favorites = favoritePokemonService.getFavoritesByUser(userId);
        return ResponseEntity.ok(favorites);
    }

    @PostMapping("/{userId}/{pokemonId}")
    public ResponseEntity<FavoritePokemonDTO> addFavoritePokemon(@PathVariable Long userId, @PathVariable Long pokemonId) {
        FavoritePokemonDTO favorite = favoritePokemonService.addFavoritePokemon(userId, pokemonId);
        return ResponseEntity.ok(favorite);
    }
}
