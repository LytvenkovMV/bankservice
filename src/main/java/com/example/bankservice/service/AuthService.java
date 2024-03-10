package com.example.bankservice.service;

import com.example.bankservice.configuration.jwt.JwtUtils;
import com.example.bankservice.dto.JwtResponseDto;
import com.example.bankservice.dto.LoginRequestDto;
import com.example.bankservice.mapper.MapStructMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final MapStructMapper mapStructMapper;

    public AuthService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, MapStructMapper mapStructMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.mapStructMapper = mapStructMapper;
    }

    public JwtResponseDto authUser(@RequestBody LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String[] roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        return mapStructMapper.fromTokenAndTypeAndUserNameAndRoles(jwt, "Bearer", userDetails.getUsername(), roles);
    }
}
