package com.sisdis.pokemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/simulate")
public class ProxyController {

    private final String flaskApiUrl = "http://localhost:5000/simulate"; // Base URL de la API Flask
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/db-error")
    public String simulateDbError(Model model) {
        return forwardToFlask("/db-error", model);
    }

    @GetMapping("/api-error")
    public String simulateApiError(Model model) {
        return forwardToFlask("/api-error", model);
    }

    @GetMapping("/file-error")
    public String simulateFileError(Model model) {
        return forwardToFlask("/file-error", model);
    }

    @GetMapping("/db-connection-error")
    public String simulateDbConnectionError(Model model) {
        return forwardToFlask("/db-connection-error", model);
    }

    @GetMapping("/db-integrity-error")
    public String simulateDbIntegrityError(Model model) {
        return forwardToFlask("/db-integrity-error", model);
    }

    @GetMapping("/db-syntax-error")
    public String simulateDbSyntaxError(Model model) {
        return forwardToFlask("/db-syntax-error", model);
    }

    @GetMapping("/api-auth-error")
    public String simulateApiAuthError(Model model) {
        return forwardToFlask("/api-auth-error", model);
    }

    @GetMapping("/api-rate-limit-error")
    public String simulateApiRateLimitError(Model model) {
        return forwardToFlask("/api-rate-limit-error", model);
    }

    // --------------------------------------------------------------------------------
    
    private String forwardToFlask(String endpoint, Model model) {
        String url = flaskApiUrl + endpoint;
        try {
            // Reenvía la solicitud a Flask
            restTemplate.getForEntity(url, String.class);
            return "redirect:/simulate"; // Si no hay error, redirige a la página de simulación
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Procesa los errores HTTP (4xx y 5xx)
            String responseBody = e.getResponseBodyAsString();
            model.addAttribute("status", e.getStatusCode().value());
            model.addAttribute("message", extractErrorMessage(responseBody));
            return "error"; // Redirige a la página de error
        } catch (Exception e) {
            // Procesa otros errores (como problemas de conexión)
            model.addAttribute("status", 500);
            model.addAttribute("message", "Error connecting to Flask API: " + e.getMessage());
            return "error"; // Redirige a la página de error
        }
    }

    private String extractErrorMessage(String responseBody) {
        // Extrae el mensaje de error del JSON devuelto por Flask
        try {
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            java.util.Map<String, String> errorMap = objectMapper.readValue(responseBody, java.util.Map.class);
            return errorMap.getOrDefault("message", "An unexpected error occurred.");
        } catch (Exception e) {
            return "An unexpected error occurred while processing the error response.";
        }
    }
}
