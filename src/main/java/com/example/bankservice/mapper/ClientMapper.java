package com.example.bankservice.mapper;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public Client fromAddClientRequestDto(AddClientRequestDto addClientRequestDto)
    {
        Client client = new Client();

        client.setSurname(addClientRequestDto.getSurname());
        client.setName(addClientRequestDto.getName());
        client.setMiddlename(addClientRequestDto.getMiddlename());
        client.setBirthDate(addClientRequestDto.getBirthDate());
        client.setBalance(addClientRequestDto.getBalance());

        return client;
    }
}
