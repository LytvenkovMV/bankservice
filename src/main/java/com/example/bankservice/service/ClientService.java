package com.example.bankservice.service;

import com.example.bankservice.configuration.jwt.JwtUtils;
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
    private final JwtUtils jwtUtils;

    public ClientService(ClientRepository clientRepository, PhoneRepository phoneRepository, EmailRepository emailRepository, JwtUtils jwtUtils) {
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.jwtUtils = jwtUtils;
    }

    public String greeting(String headerAuth) {
        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwt(headerAuth));
        return "Приветствуем, " + username + "! Это сервис для клиентов.";
    }

    public void addPhone(String headerAuth, ModifyPhoneRequestDto modifyPhoneRequestDto) {

        log.info("Получен запрос на добавление телефона клиента: " + modifyPhoneRequestDto.toString());

        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwt(headerAuth));
        if (!clientRepository.existsClientByUsername(username))
            ExSender.sendBadRequest("Клиент с таким логином не найден в БД");
        if (phoneRepository.existsPhoneByPhone(modifyPhoneRequestDto.getPhone()))
            ExSender.sendBadRequest("Такой номер телефона уже существует");

        Phone phone = new Phone();
        phone.setClient(clientRepository.findClientByUsername(username));
        phone.setPhone(modifyPhoneRequestDto.getPhone());
        phoneRepository.save(phone);

        log.info("Телефон сохранен в БД");
    }

    public void addEmail(String headerAuth, ModifyEmailRequestDto modifyEmailRequestDto) {

        log.info("Получен запрос на добавление email клиента: " + modifyEmailRequestDto.toString());

        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwt(headerAuth));
        if (!clientRepository.existsClientByUsername(username))
            ExSender.sendBadRequest("Клиент с таким логином не найден в БД");
        if (emailRepository.existsEmailByEmail(modifyEmailRequestDto.getEmail()))
            ExSender.sendBadRequest("Такой email уже существует");

        Email email = new Email();
        email.setClient(clientRepository.findClientByUsername(username));
        email.setEmail(modifyEmailRequestDto.getEmail());
        emailRepository.save(email);

        log.info("Email сохранен в БД");
    }

    public void deletePhone(String headerAuth, ModifyPhoneRequestDto modifyPhoneRequestDto) {

        log.info("Получен запрос на удаление телефона клиента: " + modifyPhoneRequestDto.toString());

        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwt(headerAuth));
        if (!clientRepository.existsClientByUsername(username))
            ExSender.sendBadRequest("Клиент с таким логином не найден в БД");
        if (!phoneRepository.existsPhoneByPhone(modifyPhoneRequestDto.getPhone()))
            ExSender.sendBadRequest("Такой номер телефона не найден в БД");

        Client client = clientRepository.findClientByUsername(username);
        List<Phone> existingPhones = client.getPhones();
        if (existingPhones.size() < 2)
            ExSender.sendBadRequest("Нельзя удалить последний номер телефона");

        Phone phone = phoneRepository.findByPhone(modifyPhoneRequestDto.getPhone());
        if (!client.getUsername().equals(phone.getClient().getUsername()))
            ExSender.sendBadRequest("Этот номер телефона принадлежит другому клиенту");

        phoneRepository.delete(phone);

        log.info("Телефон удален из БД");
    }

    public void deleteEmail(String headerAuth, ModifyEmailRequestDto modifyEmailRequestDto) {

        log.info("Получен запрос на удаление email клиента: " + modifyEmailRequestDto.toString());

        String username = jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwt(headerAuth));
        if (!clientRepository.existsClientByUsername(username))
            ExSender.sendBadRequest("Клиент с таким логином не найден в БД");
        if (!emailRepository.existsEmailByEmail(modifyEmailRequestDto.getEmail()))
            ExSender.sendBadRequest("Такой email не найден в БД");

        Client client = clientRepository.findClientByUsername(username);
        List<Email> existingEmails = client.getEmails();
        if (existingEmails.size() < 2)
            ExSender.sendBadRequest("Нельзя удалить последний email");

        Email email = emailRepository.findByEmail(modifyEmailRequestDto.getEmail());
        if (!client.getUsername().equals(email.getClient().getUsername()))
            ExSender.sendBadRequest("Этот email принадлежит другому клиенту");

        emailRepository.delete(email);

        log.info("Email удален из БД");
    }
}
