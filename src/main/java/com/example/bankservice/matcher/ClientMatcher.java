package com.example.bankservice.matcher;

import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;

import java.util.List;

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

    public static boolean isExistsWithLogin(List<Client> clients, String sample) {
        return clients.stream()
                .map(Client::getLogin)
                .anyMatch(sample::equals);
    }

    public static boolean isExistsWithId(List<Client> clients, Integer id) {
        return clients.stream()
                .map(Client::getId)
                .anyMatch(i -> i == id);
    }

    public static boolean isExistsWithPhone(List<Client> clients, String sample) {
        return clients.stream()
                .flatMap(client -> client.getPhones().stream())
                .map(Phone::getPhone)
                .anyMatch(sample::equals);
    }

    public static boolean isExistsWithEmail(List<Client> clients, String sample) {
        return clients.stream()
                .flatMap(client -> client.getEmails().stream())
                .map(Email::getEmail)
                .anyMatch(sample::equals);
    }
}
