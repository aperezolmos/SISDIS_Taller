package com.sisdis.pokemanager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class FlaskService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String flaskApiUrl = "http://localhost:5000"; // URL de la API Flask


    public List<Map<String, String>> listPokemons(int limit, int offset) {
        String url = flaskApiUrl + "/list-pokemon?limit=" + limit + "&offset=" + offset;
        return restTemplate.getForObject(url, List.class);
    }


    public Map<String, Object> searchPokemons(String name, int limit, int offset) {
        String url = flaskApiUrl + "/search-pokemon?limit=" + limit + "&offset=" + offset;
        if (name != null && !name.isEmpty()) {
            url += "&name=" + name;
        }
        return restTemplate.getForObject(url, Map.class);
    }


    public Map<String, Object> getDailyPokemon(int pokemonId) {
        String url = flaskApiUrl + "/pokemon-daily/" + pokemonId;
        return restTemplate.getForObject(url, Map.class);
    }
}
