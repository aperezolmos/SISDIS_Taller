package com.sisdis.pokemanager.service;

import com.sisdis.pokemanager.dto.PokemonDTO;
import com.sisdis.pokemanager.model.Pokemon;
import com.sisdis.pokemanager.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {

    @Autowired
    private PokemonRepository pokemonRepository;


    public PokemonDTO findById(Long id) {
        return pokemonRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Pokemon not found with id: " + id));
    }

    public List<Pokemon> findByName(String name) {
        return pokemonRepository.findByNameContainingIgnoreCase(name);
    }

    public List<PokemonDTO> findAll() {
        return pokemonRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }

    public PokemonDTO registerPokemon(Pokemon pokemon) {
        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return convertToDTO(savedPokemon);
    }

    public PokemonDTO updatePokemon(Pokemon pokemon) {
        Pokemon updatedPokemon = pokemonRepository.save(pokemon);
        return convertToDTO(updatedPokemon);
    }

    public void deleteById(Long id) {
        pokemonRepository.deleteById(id);
    }

    
    public PokemonDTO convertToDTO(Pokemon pokemon) {
        return new PokemonDTO(pokemon.getId(), pokemon.getName(), pokemon.getImageUrl());
    }

    public Pokemon convertToEntity(PokemonDTO pokemonDTO) {
        Pokemon pokemon = new Pokemon();
        pokemon.setId(pokemonDTO.getId());
        pokemon.setName(pokemonDTO.getName());
        pokemon.setImageUrl(pokemonDTO.getImageUrl());
        return pokemon;
    }
}
