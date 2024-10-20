package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already registered.");
        }
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Since we're using Basic Auth, login is handled by Spring Security.
    // However, we can provide an endpoint to check authentication.
    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("User is authenticated.");
    }
}
