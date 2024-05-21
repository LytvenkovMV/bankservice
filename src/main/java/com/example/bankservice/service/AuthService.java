package com.example.bankservice.service;

import com.example.bankservice.configuration.jwt.JwtUtils;
import com.example.bankservice.dto.JwtResponseDto;
import com.example.bankservice.dto.LoginRequestDto;
import com.example.bankservice.mapper.MapStructMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final MapStructMapper mapStructMapper;

    public JwtResponseDto authUser(@RequestBody LoginRequestDto loginRequestDto) {

        log.info("Поступил запрос на аутентификацию клиента " + loginRequestDto.toString());

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

    public String getUsername(String headerAuth) {
        return jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwt(headerAuth));
    }
}
