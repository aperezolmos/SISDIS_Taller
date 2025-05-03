package com.sisdis.pokemanager.controller;

import com.sisdis.pokemanager.dto.UserDTO;
import com.sisdis.pokemanager.model.User;
import com.sisdis.pokemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO userDTO = userService.findByUsername(username);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO userDTO = userService.registerUser(user);
        return ResponseEntity.ok(userDTO);
    }
}
