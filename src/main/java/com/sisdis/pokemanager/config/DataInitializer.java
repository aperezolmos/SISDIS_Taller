package com.sisdis.pokemanager.config;

import com.sisdis.pokemanager.model.User;
import com.sisdis.pokemanager.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        
        String USUARIO = "amanda";
        String PASSWORD = "perez123";
        String ROL = "admin";
        
        return args -> {
            if (userRepository.findByUsername(USUARIO).isEmpty()) {
                User user = new User();
                user.setUsername(USUARIO);
                user.setPassword(passwordEncoder.encode(PASSWORD));
                user.setRole(ROL);
                userRepository.save(user);
            }
        };
    }
}
