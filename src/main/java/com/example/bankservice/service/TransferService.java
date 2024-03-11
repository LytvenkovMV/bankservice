package com.example.bankservice.service;

import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.exception.ExSender;
import com.example.bankservice.repository.ClientRepository;
import jakarta.persistence.LockModeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class TransferService {

    private final ClientRepository clientRepository;
    private final double maxAmount;

    public TransferService(ClientRepository clientRepository,
                           @Value("${transfer-service.max-amount}") double maxAmount) {
        this.clientRepository = clientRepository;
        this.maxAmount = maxAmount;
    }

    public String greeting(String username) {
        return "Приветствуем, " + username + "! Это сервис переводов.";
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void transfer(String senderUsername, TransferRequestDto transferRequestDto) {

        log.info("Получен запрос на перевод " + transferRequestDto.toString());

        String recipientUsername = transferRequestDto.getRecipientUsername();
        double amount = transferRequestDto.getAmount();

        if (senderUsername.equals(recipientUsername))
            ExSender.sendBadRequest("Логин отправителя и логин получателя совпадают");
        if (amount <= 0.00 || amount > maxAmount) ExSender.sendBadRequest("Некорректная сумма для перевода");
        if (!clientRepository.existsClientByUsername(senderUsername))
            ExSender.sendBadRequest("Отправитель с таким логином не найден в БД");
        if (!clientRepository.existsClientByUsername(recipientUsername))
            ExSender.sendBadRequest("Получатель с таким логином не найден в БД");

        Client sender = clientRepository.findClientByUsername(senderUsername);
        Client recipient = clientRepository.findClientByUsername(recipientUsername);
        Double senderCurrBalance = sender.getCurrBalance();
        Double recipientCurrBalance = recipient.getCurrBalance();

        if (amount > senderCurrBalance) ExSender.sendBadRequest("У отправителя недостаточно средств для перевода");

        sender.setCurrBalance(senderCurrBalance - amount);
        recipient.setCurrBalance(recipientCurrBalance + amount);
        clientRepository.save(sender);
        clientRepository.save(recipient);

        log.info("Перевод выполнен");
    }
}
