package com.sisdis.pokemanager.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorite_pokemons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

    @Column(name = "date_added")
    private LocalDateTime dateAdded = LocalDateTime.now();
}
