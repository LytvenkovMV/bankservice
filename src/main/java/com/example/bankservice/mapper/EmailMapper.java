package com.example.bankservice.mapper;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailMapper {

    public List<Email> fromAddClientRequestDtoAndClient(AddClientRequestDto addClientRequestDto, Client client) {
        List<Email> emails = new ArrayList<>();

        String[] stringEmails = addClientRequestDto.getEmails();

        for (String stringEmail : stringEmails) {
            Email email = new Email();
            email.setEmail(stringEmail);
            email.setClient(client);
            emails.add(email);
        }

        return emails;
    }
}
