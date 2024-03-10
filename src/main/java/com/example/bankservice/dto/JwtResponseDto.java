package com.example.bankservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {

    private String token;
    private String type;
    private String username;
    private String[] roles;
}
