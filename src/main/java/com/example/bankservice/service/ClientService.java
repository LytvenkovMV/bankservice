package com.example.bankservice.service;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.dto.ModifyEmailRequestDto;
import com.example.bankservice.dto.ModifyPhoneRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import com.example.bankservice.exception.ExSender;
import com.example.bankservice.mapper.MapStructMapper;
import com.example.bankservice.repository.ClientRepository;
import com.example.bankservice.repository.EmailRepository;
import com.example.bankservice.repository.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final MapStructMapper mapStructMapper;
    private final Double maxInitBalance;

    public ClientService(ClientRepository clientRepository,
                         PhoneRepository phoneRepository,
                         EmailRepository emailRepository,
                         MapStructMapper mapStructMapper,
                         @Value("${client-service.max-init-balance}") Double maxInitBalance) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.mapStructMapper = mapStructMapper;
        this.maxInitBalance = maxInitBalance;
    }


    @Transactional
    public void addClient(AddClientRequestDto addClientRequestDto) {

        log.info("Получен запрос на добавление клиента: " + addClientRequestDto.toString());

        Client client = mapStructMapper.fromAddClientRequestDto(addClientRequestDto);
        List<Phone> phones = mapStructMapper.fromStringPhonesAndClient(addClientRequestDto.getPhones(), client);
        List<Email> emails = mapStructMapper.fromStringEmailsAndClient(addClientRequestDto.getEmails(), client);

        if (client.getInitBalance() <= 0 || client.getInitBalance() > maxInitBalance)
            ExSender.sendBadRequest("Некорректный начальный баланс");
        if (clientRepository.existsClientByLogin(client.getLogin()))
            ExSender.sendBadRequest("Клиент с таким логином уже существует");
        for (Phone phone : phones) {
            if (phoneRepository.existsPhoneByPhone(phone.getPhone()))
                ExSender.sendBadRequest("Клиент с таким номером телефона уже существует");
        }
        for (Email email : emails) {
            if (emailRepository.existsEmailByEmail(email.getEmail()))
                ExSender.sendBadRequest("Клиент с таким email уже существует");
        }

        clientRepository.save(client);
        phoneRepository.saveAll(phones);
        emailRepository.saveAll(emails);

        log.info("Клиент сохранен в БД");
    }


    public void addPhone(ModifyPhoneRequestDto modifyPhoneRequestDto) {

        log.info("Получен запрос на добавление телефона клиента: " + modifyPhoneRequestDto.toString());

        if (!clientRepository.existsClientById(modifyPhoneRequestDto.getClientId()))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");
        if (phoneRepository.existsPhoneByPhone(modifyPhoneRequestDto.getPhone()))
            ExSender.sendBadRequest("Такой номер телефона уже существует");

        Phone phone = new Phone();
        phone.setClient(clientRepository.getReferenceById(modifyPhoneRequestDto.getClientId()));
        phone.setPhone(modifyPhoneRequestDto.getPhone());
        phoneRepository.save(phone);

        log.info("Телефон сохранен в БД");
    }


    public void addEmail(ModifyEmailRequestDto modifyEmailRequestDto) {

        log.info("Получен запрос на добавление email клиента: " + modifyEmailRequestDto.toString());

        if (!clientRepository.existsClientById(modifyEmailRequestDto.getClientId()))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");
        if (emailRepository.existsEmailByEmail(modifyEmailRequestDto.getEmail()))
            ExSender.sendBadRequest("Такой email уже существует");

        Email email = new Email();
        email.setClient(clientRepository.getReferenceById(modifyEmailRequestDto.getClientId()));
        email.setEmail(modifyEmailRequestDto.getEmail());
        emailRepository.save(email);

        log.info("Email сохранен в БД");
    }


    public void deletePhone(ModifyPhoneRequestDto modifyPhoneRequestDto) {

        log.info("Получен запрос на удаление телефона клиента: " + modifyPhoneRequestDto.toString());

        if (!clientRepository.existsClientById(modifyPhoneRequestDto.getClientId()))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");
        if (!phoneRepository.existsPhoneByPhone(modifyPhoneRequestDto.getPhone()))
            ExSender.sendBadRequest("Такой номер телефона не найден в БД");

        Client client = clientRepository.getReferenceById(modifyPhoneRequestDto.getClientId());
        List<Phone> existingPhones = client.getPhones();
        if (existingPhones.size() < 2)
            ExSender.sendBadRequest("Нельзя удалить последний номер телефона");

        Phone phone = phoneRepository.findByPhone(modifyPhoneRequestDto.getPhone());
        if (modifyPhoneRequestDto.getClientId() != phone.getClient().getId())
            ExSender.sendBadRequest("Этот номер телефона принадлежит другому клиенту");

        phoneRepository.delete(phone);

        log.info("Телефон удален из БД");
    }


    public void deleteEmail(ModifyEmailRequestDto modifyEmailRequestDto) {

        log.info("Получен запрос на удаление email клиента: " + modifyEmailRequestDto.toString());

        if (!clientRepository.existsClientById(modifyEmailRequestDto.getClientId()))
            ExSender.sendBadRequest("Клиент с таким ИД не найден в БД");
        if (!emailRepository.existsEmailByEmail(modifyEmailRequestDto.getEmail()))
            ExSender.sendBadRequest("Такой email не найден в БД");

        Client client = clientRepository.getReferenceById(modifyEmailRequestDto.getClientId());
        List<Email> existingEmails = client.getEmails();
        if (existingEmails.size() < 2)
            ExSender.sendBadRequest("Нельзя удалить последний email");

        Email email = emailRepository.findByEmail(modifyEmailRequestDto.getEmail());
        if (modifyEmailRequestDto.getClientId() != email.getClient().getId())
            ExSender.sendBadRequest("Этот email принадлежит другому клиенту");

        emailRepository.delete(email);

        log.info("Email удален из БД");
    }
}
