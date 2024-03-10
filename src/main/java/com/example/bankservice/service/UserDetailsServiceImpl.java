package com.example.bankservice.service;

import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientRepository clientRepository;

    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!clientRepository.existsClientByUsername(username))
            throw new UsernameNotFoundException("Клиент с таким логином не найден в БД");

        Client client = clientRepository.findClientByUsername(username);

        return UserDetailsImpl.build(client.getUsername(), client.getPassword(), client.getRoles());
    }
}
