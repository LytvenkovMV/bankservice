package com.example.bankservice.service;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import com.example.bankservice.mapper.ClientMapper;
import com.example.bankservice.mapper.EmailMapper;
import com.example.bankservice.mapper.PhoneMapper;
import com.example.bankservice.matcher.ClientMatcher;
import com.example.bankservice.repository.ClientRepository;
import com.example.bankservice.repository.EmailRepository;
import com.example.bankservice.repository.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@Slf4j
public class ClientService {

    private final ClientMatcher clientMatcher;
    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final ClientMapper clientMapper;
    private final PhoneMapper phoneMapper;
    private final EmailMapper emailMapper;

    public ClientService(ClientMatcher clientMatcher,
                         ClientRepository clientRepository,
                         PhoneRepository phoneRepository,
                         EmailRepository emailRepository,
                         ClientMapper clientMapper,
                         PhoneMapper phoneMapper,
                         EmailMapper emailMapper) {
        this.clientMatcher = clientMatcher;
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.clientMapper = clientMapper;
        this.phoneMapper = phoneMapper;
        this.emailMapper = emailMapper;
    }

    @Transactional
    public void addClient(AddClientRequestDto addClientRequestDto) {

        log.info("Получен запрос на добавление клиента: " + addClientRequestDto.toString());

        Client client = clientMapper.fromAddClientRequestDto(addClientRequestDto);
        List<Phone> phones = phoneMapper.fromAddClientRequestDtoAndClient(addClientRequestDto, client);
        List<Email> emails = emailMapper.fromAddClientRequestDtoAndClient(addClientRequestDto, client);



        if (clientMatcher.isExistsWithSurname(client.getSurname())) {
            log.info("Клиент с такой фамилией уже существует!!!!!!!!!");
            ////////////throw new Exception();
        }


        clientRepository.save(client);
        phoneRepository.saveAll(phones);
        emailRepository.saveAll(emails);

        log.info("Клиент сохранен в БД");
    }
}
