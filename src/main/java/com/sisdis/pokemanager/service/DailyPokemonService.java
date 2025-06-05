package com.sisdis.pokemanager.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class DailyPokemonService {

    private static final int MAX_POKEMON_ID = 850;
    private int dailyPokemonId;

    @PostConstruct
    public void init() {
        Random random = new Random();
        dailyPokemonId = random.nextInt(MAX_POKEMON_ID) + 1;
    }

    public int getDailyPokemonId() {
        return dailyPokemonId;
    }
}
