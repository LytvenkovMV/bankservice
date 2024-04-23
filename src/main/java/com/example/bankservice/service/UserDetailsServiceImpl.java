package com.example.bankservice.service;

import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Role;
import com.example.bankservice.enumerator.ERole;
import com.example.bankservice.mapper.MapStructMapper;
import com.example.bankservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final MapStructMapper mapStructMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!clientRepository.existsClientByUsername(username))
            throw new UsernameNotFoundException("Клиент с таким логином не найден в БД");

        Client client = clientRepository.findClientByUsername(username);
        Set<Role> roles = new HashSet<>();
        roles.add(mapStructMapper.fromIdAndName(1, ERole.ROLE_CLIENT));

        return UserDetailsImpl.build(client.getUsername(), client.getPassword(), roles);
    }
}
