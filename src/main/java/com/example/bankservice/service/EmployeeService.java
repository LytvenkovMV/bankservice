package com.example.bankservice.service;

import com.example.bankservice.dto.AddClientRequestDto;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;
    private final EmailRepository emailRepository;
    private final MapStructMapper mapStructMapper;
    private final BigDecimal maxInitBalance;

    public EmployeeService(PasswordEncoder passwordEncoder,
                           ClientRepository clientRepository,
                           PhoneRepository phoneRepository,
                           EmailRepository emailRepository,
                           MapStructMapper mapStructMapper,
                           @Value("${employee-service.max-init-balance}") String maxInitBalance) {
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
        this.phoneRepository = phoneRepository;
        this.emailRepository = emailRepository;
        this.mapStructMapper = mapStructMapper;
        this.maxInitBalance = new BigDecimal(maxInitBalance);
    }


    @Transactional
    public void addClient(AddClientRequestDto addClientRequestDto) {

        log.info("Получен запрос на добавление клиента: " + addClientRequestDto.toString());

        BigDecimal initBalance = new BigDecimal(addClientRequestDto.getBalance());
        if ((initBalance.compareTo(new BigDecimal(0)) <= 0) || (initBalance.compareTo(maxInitBalance) > 0))
            ExSender.sendBadRequest("Некорректный начальный баланс");
        if (clientRepository.existsClientByUsername(addClientRequestDto.getUsername()))
            ExSender.sendBadRequest("Клиент с таким логином уже существует");
        for (String phone : addClientRequestDto.getPhones()) {
            if (phoneRepository.existsPhoneByPhone(phone))
                ExSender.sendBadRequest("Клиент с таким номером телефона уже существует");
        }
        for (String email : addClientRequestDto.getEmails()) {
            if (emailRepository.existsEmailByEmail(email))
                ExSender.sendBadRequest("Клиент с таким email уже существует");
        }

        String encPassword = passwordEncoder.encode(addClientRequestDto.getPassword());
        Client client = mapStructMapper.fromAddClientRequestDtoAndEncPassword(addClientRequestDto, encPassword);
        List<Phone> phones = mapStructMapper.fromStringPhonesAndClient(addClientRequestDto.getPhones(), client);
        List<Email> emails = mapStructMapper.fromStringEmailsAndClient(addClientRequestDto.getEmails(), client);

        clientRepository.save(client);
        phoneRepository.saveAll(phones);
        emailRepository.saveAll(emails);

        log.info("Клиент сохранен в БД");
    }
}
