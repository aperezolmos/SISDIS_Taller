package com.sisdis.pokemanager.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePokemonDTO {
    private Long id;
    private Long pokemonId;
    private LocalDateTime dateAdded;
}
