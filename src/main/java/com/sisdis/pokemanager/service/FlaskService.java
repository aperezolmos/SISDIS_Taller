package com.sisdis.pokemanager.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class FlaskService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String flaskApiUrl = "http://localhost:5000"; // URL de la API Flask

    
    public boolean validateUser(String username, String password) {
        try {
            String url = flaskApiUrl + "/validate-user";
            Map<String, String> credentials = Map.of("username", username, "password", password);
            Boolean isValid = restTemplate.postForObject(url, credentials, Boolean.class);
            return isValid != null && isValid;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Map<String, String>> listPokemons(int limit, int offset) {
        String url = flaskApiUrl + "/list-pokemon?limit=" + limit + "&offset=" + offset;
        return restTemplate.getForObject(url, List.class);
    }
}
