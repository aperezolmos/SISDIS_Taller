package com.sisdis.pokemanager.config;

import com.sisdis.pokemanager.model.FavoritePokemon;
import com.sisdis.pokemanager.model.Pokemon;
import com.sisdis.pokemanager.model.User;
import com.sisdis.pokemanager.repository.FavoritePokemonRepository;
import com.sisdis.pokemanager.repository.PokemonRepository;
import com.sisdis.pokemanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            PokemonRepository pokemonRepository,
            FavoritePokemonRepository favoritePokemonRepository
    ) {
        String USUARIO = "amanda";
        String PASSWORD = "perez123";
        String ROL = "admin";

        return args -> {
            User user = userRepository.findByUsername(USUARIO).orElse(null);
            if (user == null) {
                user = new User();
                user.setUsername(USUARIO);
                user.setPassword(passwordEncoder.encode(PASSWORD));
                user.setRole(ROL);
                user = userRepository.save(user);
            }

            // Añadir todos los pokemons como favoritos de 'amanda'
            for (Pokemon pokemon : pokemonRepository.findAll()) {
                boolean exists = favoritePokemonRepository.existsByUserAndPokemon(user, pokemon);
                if (!exists) {
                    FavoritePokemon fav = new FavoritePokemon();
                    fav.setUser(user);
                    fav.setPokemon(pokemon);
                    favoritePokemonRepository.save(fav);
                }
            }
        };
    }
}
