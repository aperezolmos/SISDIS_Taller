package com.sisdis.pokemanager.model;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pokemons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "external_id")    // PokeAPI ID
    private Integer externalId;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoritePokemon> favoritedByUsers = new ArrayList<>();
}
