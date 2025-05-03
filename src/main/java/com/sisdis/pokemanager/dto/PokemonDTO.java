package com.sisdis.pokemanager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonDTO {
    private Long id;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
}
