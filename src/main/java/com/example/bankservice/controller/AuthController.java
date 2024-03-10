package com.example.bankservice.controller;

import com.example.bankservice.dto.JwtResponseDto;
import com.example.bankservice.dto.LoginRequestDto;
import com.example.bankservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankservice/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Приветствие")
    @GetMapping()
    public ResponseEntity<String> greeting() {
        return ResponseEntity.status(HttpStatus.OK).body("Приветствуем! Это сервис аутентификации.");
    }

    @Operation(summary = "Аутентификация клиента")
    @PostMapping("/signin")
    public JwtResponseDto authUser(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.authUser(loginRequestDto);
    }
}
