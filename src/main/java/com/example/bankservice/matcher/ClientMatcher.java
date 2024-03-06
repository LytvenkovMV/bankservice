package com.example.bankservice.matcher;

import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import org.springframework.stereotype.Component;

import java.util.List;

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
                .map(Client::getLogin)
                .anyMatch(sample::equals);
    }

    public boolean isExistsWithPhone(List<Client> clients, String sample) {
        return clients.stream()
                .flatMap(client -> client.getPhones().stream())
                .map(Phone::getPhone)
                .anyMatch(sample::equals);
    }

    public boolean isExistsWithEmail(List<Client> clients, String sample) {
        return clients.stream()
                .flatMap(client -> client.getEmails().stream())
                .map(Email::getEmail)
                .anyMatch(sample::equals);
    }
}
