package com.example.bankservice.service;

import com.example.bankservice.dto.ModifyEmailRequestDto;
import com.example.bankservice.dto.ModifyPhoneRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.entity.Email;
import com.example.bankservice.entity.Phone;
import com.example.bankservice.exception.ExSender;
import com.example.bankservice.repository.ClientRepository;
import com.example.bankservice.repository.EmailRepository;
import com.example.bankservice.repository.PhoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;

    public ClientService(ClientRepository clientRepository,
                         PhoneRepository phoneRepository,
                         EmailRepository emailRepository) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
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
