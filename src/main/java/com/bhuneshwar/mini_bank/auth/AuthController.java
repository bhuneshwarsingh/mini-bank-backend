package com.bhuneshwar.mini_bank.auth;

import com.bhuneshwar.mini_bank.dto.RegisterRequest;
import com.bhuneshwar.mini_bank.dto.LoginRequest;
import com.bhuneshwar.mini_bank.dto.AuthResponse;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service)
    {
        this.service=service;
    }
    @PostMapping("/register")
    private ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req)
    {
        return ResponseEntity.ok(service.register(req));
    }
    @PostMapping("/login")
    private ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req)
    {
        return ResponseEntity.ok(service.login(req));
    }
}
