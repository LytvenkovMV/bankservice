package com.example.bankservice.matcher;

import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Component
public class ClientMatcher {
    ClientRepository clientRepository;

    public ClientMatcher(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public boolean isExistsWithLogin(String login) {
        Client probe = new Client();
        probe.setLogin(login);
        Example<Client> example = Example.of(probe,
                ExampleMatcher.matching()
                        .withIgnorePaths("id")
                        .withMatcher("login", exact()));
        return clientRepository.exists(example);
    }
}