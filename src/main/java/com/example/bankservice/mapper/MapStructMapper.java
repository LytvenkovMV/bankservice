package com.example.bankservice.mapper;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.dto.JwtResponseDto;
import com.example.bankservice.dto.SearchClientResponseDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import com.example.bankservice.entity.Role;
import com.example.bankservice.enumerator.ERole;
import com.example.bankservice.service.UserDetailsImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phones", ignore = true)
    @Mapping(target = "emails", ignore = true)
    @Mapping(target = "password", source = "encPassword")
    @Mapping(target = "initBalance", source = "addClientRequestDto.balance")
    @Mapping(target = "currBalance", source = "addClientRequestDto.balance")
    Client fromAddClientRequestDtoAndEncPassword(AddClientRequestDto addClientRequestDto, String encPassword);

    JwtResponseDto fromTokenAndTypeAndUserNameAndRoles(String token, String type, String username, String[] roles);

    SearchClientResponseDto fromClient(Client client);

    @Mapping(target = "id", ignore = true)
    Phone fromPhoneAndClient(String phone, Client client);

    @Mapping(target = "id", ignore = true)
    Email fromEmailAndClient(String email, Client client);

    Role fromIdAndName(Integer id, ERole name);

    default List<Phone> fromStringPhonesAndClient(String[] stringPhones, Client client) {
        List<Phone> phones = new ArrayList<>();
        for (String p : stringPhones) {
            phones.add(fromPhoneAndClient(p, client));
        }
        return phones;
    }

    default List<Email> fromStringEmailsAndClient(String[] stringEmails, Client client) {
        List<Email> emails = new ArrayList<>();
        for (String e : stringEmails) {
            emails.add(fromEmailAndClient(e, client));
        }
        return emails;
    }

    default String[] fromPhones(List<Phone> phones) {
        return phones.stream().map(Phone::getPhone).toArray(String[]::new);
    }

    default String[] fromEmails(List<Email> emails) {
        return emails.stream().map(Email::getEmail).toArray(String[]::new);
    }
}
