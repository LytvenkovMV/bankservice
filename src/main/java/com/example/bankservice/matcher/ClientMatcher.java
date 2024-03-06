package com.example.bankservice.matcher;

import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Component
public class ClientMatcher {

//    public boolean isExistsWithLogin(String login) {
//        Client probe = new Client();
//        probe.setLogin(login);
//        Example<Client> example = Example.of(probe,
//                ExampleMatcher.matching()
//                        .withIgnorePaths("id")
//                        .withMatcher("login", exact()));
//        return clientRepository.exists(example);
//    }

    public boolean isExistsWithLogin(List<Client> clients, String sample) {
        return clients.stream()
                .map(c -> c.getLogin())
                .anyMatch(sample::equals);
    }

    public boolean isExistsWithPhone(List<Client> clients, String sample) {
        return clients.stream()
                .flatMap(client -> client.getPhones().stream())
                .map(p -> p.getPhone())
                .anyMatch(sample::equals);
    }

    public boolean isExistsWithEmail(List<Client> clients, String sample) {
        return clients.stream()
                .flatMap(client -> client.getEmails().stream())
                .map(s -> s.getEmail())
                .anyMatch(sample::equals);
    }
}
