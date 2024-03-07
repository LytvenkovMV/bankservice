package com.example.bankservice.service;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.dto.ModifyEmailRequestDto;
import com.example.bankservice.dto.ModifyPhoneRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import com.example.bankservice.exception.ExSender;
import com.example.bankservice.mapper.ClientMapper;
import com.example.bankservice.mapper.EmailMapper;
import com.example.bankservice.mapper.PhoneMapper;
import com.example.bankservice.matcher.ClientMatcher;
import com.example.bankservice.repository.ClientRepository;
import com.example.bankservice.repository.EmailRepository;
import com.example.bankservice.repository.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final ClientMapper clientMapper;
    private final PhoneMapper phoneMapper;
    private final EmailMapper emailMapper;
    private final Double maxInitBalance;

    public ClientService(ClientRepository clientRepository,
                         PhoneRepository phoneRepository,
                         EmailRepository emailRepository,
                         ClientMapper clientMapper,
                         PhoneMapper phoneMapper,
                         EmailMapper emailMapper,
                         @Value("${client-service.max-init-balance}") Double maxInitBalance) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.clientMapper = clientMapper;
        this.phoneMapper = phoneMapper;
        this.emailMapper = emailMapper;
        this.maxInitBalance = maxInitBalance;
    }


    @Transactional
    public void addClient(AddClientRequestDto addClientRequestDto) {

        log.info("Получен запрос на добавление клиента: " + addClientRequestDto.toString());

        Client client = clientMapper.fromAddClientRequestDto(addClientRequestDto);
        List<Phone> phones = phoneMapper.fromAddClientRequestDtoAndClient(addClientRequestDto, client);
        List<Email> emails = emailMapper.fromAddClientRequestDtoAndClient(addClientRequestDto, client);
        List<Client> existingClients = clientRepository.findAll();

        if (client.getInitBalance() <= 0 || client.getInitBalance() > maxInitBalance)
            ExSender.sendBadRequest("Некорректный начальный баланс");
        if (ClientMatcher.isExistsWithLogin(existingClients, client.getLogin()))
            ExSender.sendBadRequest("Клиент с таким логином уже существует");
        for (Phone phone : phones) {
            if (ClientMatcher.isExistsWithPhone(existingClients, phone.getPhone()))
                ExSender.sendBadRequest("Клиент с таким номером телефона уже существует");
        }
        for (Email email : emails) {
            if (ClientMatcher.isExistsWithEmail(existingClients, email.getEmail()))
                ExSender.sendBadRequest("Клиент с таким email уже существует");
        }

        clientRepository.save(client);
        phoneRepository.saveAll(phones);
        emailRepository.saveAll(emails);

        log.info("Клиент сохранен в БД");
    }


    public void addPhone(ModifyPhoneRequestDto modifyPhoneRequestDto) {

        log.info("Получен запрос на добавление телефона клиента: " + modifyPhoneRequestDto.toString());

        Integer clientId = modifyPhoneRequestDto.getClientId();
        List<Client> existingClients = clientRepository.findAll();

        if (!ClientMatcher.isExistsWithId(existingClients, clientId))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");
        if (ClientMatcher.isExistsWithPhone(existingClients, modifyPhoneRequestDto.getPhone()))
            ExSender.sendBadRequest("Такой номер телефона уже существует");

        Client client = clientRepository.getById(clientId);
        Phone phone = new Phone();
        phone.setClient(client);
        phone.setPhone(modifyPhoneRequestDto.getPhone());
        phoneRepository.save(phone);

        log.info("Телефон сохранен в БД");
    }


    public void addEmail(ModifyEmailRequestDto modifyEmailRequestDto) {

        log.info("Получен запрос на добавление email клиента: " + modifyEmailRequestDto.toString());

        Integer clientId = modifyEmailRequestDto.getClientId();
        List<Client> existingClients = clientRepository.findAll();

        if (!ClientMatcher.isExistsWithId(existingClients, clientId))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");
        if (ClientMatcher.isExistsWithEmail(existingClients, modifyEmailRequestDto.getEmail()))
            ExSender.sendBadRequest("Такой email уже существует");

        Client client = clientRepository.getById(clientId);
        Email email = new Email();
        email.setClient(client);
        email.setEmail(modifyEmailRequestDto.getEmail());
        emailRepository.save(email);

        log.info("Email сохранен в БД");
    }


    public void deletePhone(ModifyPhoneRequestDto modifyPhoneRequestDto) {

        log.info("Получен запрос на удаление телефона клиента: " + modifyPhoneRequestDto.toString());

        Integer clientId = modifyPhoneRequestDto.getClientId();
        List<Client> existingClients = clientRepository.findAll();

        if (!ClientMatcher.isExistsWithId(existingClients, clientId))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");

        Client client = clientRepository.getById(clientId);
        List<Phone> existingPhones = client.getPhones();

        if (existingPhones.size() < 2)
            ExSender.sendBadRequest("Нельзя удалить последний номер телефона");

        List<Phone> targetPhones = existingPhones.stream()
                .filter(p -> p.getPhone().equals(modifyPhoneRequestDto.getPhone()))
                .collect(Collectors.toList());

        if (targetPhones.isEmpty()) ExSender.sendBadRequest("У клиента нет такого номера телефона");

        phoneRepository.deleteAll(targetPhones);

        log.info("Телефон удален из БД");
    }


    public void deleteEmail(ModifyEmailRequestDto modifyEmailRequestDto) {

        log.info("Получен запрос на удаление email клиента: " + modifyEmailRequestDto.toString());

        Integer clientId = modifyEmailRequestDto.getClientId();
        List<Client> existingClients = clientRepository.findAll();

        if (!ClientMatcher.isExistsWithId(existingClients, clientId))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");

        Client client = clientRepository.getById(clientId);
        List<Email> existingEmails = client.getEmails();

        if (existingEmails.size() < 2)
            ExSender.sendBadRequest("Нельзя удалить последний email");

        List<Email> targetEmails = existingEmails.stream()
                .filter(p -> p.getEmail().equals(modifyEmailRequestDto.getEmail()))
                .collect(Collectors.toList());

        if (targetEmails.isEmpty()) ExSender.sendBadRequest("У клиента нет такого email");

        emailRepository.deleteAll(targetEmails);

        log.info("Email удален из БД");
    }
}
