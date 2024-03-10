package com.example.bankservice.controller;

import com.example.bankservice.dto.JwtResponseDto;
import com.example.bankservice.dto.LoginRequestDto;
import com.example.bankservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bankservice/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Аутентификация клиента")
    @PostMapping("/signin")
    public JwtResponseDto authUser(@RequestBody LoginRequestDto loginRequestDto) {
        return authService.authUser(loginRequestDto);
    }
}
