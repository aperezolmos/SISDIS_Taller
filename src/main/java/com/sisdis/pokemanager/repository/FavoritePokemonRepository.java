package com.sisdis.pokemanager.repository;

import com.sisdis.pokemanager.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritePokemonRepository extends JpaRepository<FavoritePokemon, Long> {
    boolean existsByUserAndPokemon(User user, Pokemon pokemon);
}
