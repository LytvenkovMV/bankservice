package com.example.bankservice.mapper;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Phone;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PhoneMapper {

    public List<Phone> fromAddClientRequestDtoAndClient(AddClientRequestDto addClientRequestDto, Client client)
    {
        List<Phone> phones = new ArrayList<>();

        String[] stringPhones = addClientRequestDto.getPhones();

        for (String stringPhone : stringPhones) {
            Phone phone = new Phone();
            phone.setPhone(stringPhone);
            phone.setClient(client);
            phones.add(phone);
        }

        return phones;
    }
}
