package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.dto.PokemonDTO;
import com.sisdis.pokemanager.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;


    @GetMapping("/search")
    public ResponseEntity<List<PokemonDTO>> searchPokemons(@RequestParam String name) {
        List<PokemonDTO> pokemonDTOs = pokemonService.findByName(name);
        return ResponseEntity.ok(pokemonDTOs);
    }
}
