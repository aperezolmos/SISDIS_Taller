package com.sisdis.pokemanager.service;

import com.sisdis.pokemanager.dto.FavoritePokemonDTO;
import com.sisdis.pokemanager.model.*;
import com.sisdis.pokemanager.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritePokemonService {

    @Autowired
    private FavoritePokemonRepository favoritePokemonRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PokemonRepository pokemonRepository;


    public List<FavoritePokemonDTO> getFavoritesByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return user.getFavoritePokemons().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FavoritePokemonDTO addFavoritePokemon(Long userId, Long pokemonId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new IllegalArgumentException("Pokemon not found with ID: " + pokemonId));

        if (!favoritePokemonRepository.existsByUserAndPokemon(user, pokemon)) {
            FavoritePokemon favoritePokemon = new FavoritePokemon();
            favoritePokemon.setUser(user);
            favoritePokemon.setPokemon(pokemon);

            FavoritePokemon savedFavorite = favoritePokemonRepository.save(favoritePokemon);
            return convertToDTO(savedFavorite);
        }
        return null;
    }

    public void removeFavoritePokemon(Long favoriteId) {
        if (!favoritePokemonRepository.existsById(favoriteId)) {
            throw new IllegalArgumentException("Favorite Pokemon not found with ID: " + favoriteId);
        }
        favoritePokemonRepository.deleteById(favoriteId);
    }


    public FavoritePokemonDTO convertToDTO(FavoritePokemon favoritePokemon) {
        return new FavoritePokemonDTO(
                favoritePokemon.getId(),
                favoritePokemon.getPokemon().getId(),
                favoritePokemon.getDateAdded()
        );
    }

    public FavoritePokemon convertToEntity(FavoritePokemonDTO dto) {
        User user = userRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getId()));
        Pokemon pokemon = pokemonRepository.findById(dto.getPokemonId())
                .orElseThrow(() -> new IllegalArgumentException("Pokemon not found with ID: " + dto.getPokemonId()));

        FavoritePokemon favoritePokemon = new FavoritePokemon();
        favoritePokemon.setUser(user);
        favoritePokemon.setPokemon(pokemon);
        return favoritePokemon;
    }
}
