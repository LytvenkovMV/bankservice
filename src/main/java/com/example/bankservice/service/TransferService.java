package com.example.bankservice.service;

import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.exception.ExSender;
import com.example.bankservice.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Transactional
    public void transfer(TransferRequestDto transferRequestDto) {

        log.info("Получен запрос на перевод " + transferRequestDto.toString());

        int senderId = transferRequestDto.getSenderId();
        int recipientId = transferRequestDto.getRecipientId();
        double amount = transferRequestDto.getAmount();

        if (senderId == recipientId) ExSender.sendBadRequest("ИД отправителя и получателя совпадают");
        if (amount <= 0.00 || amount > maxAmount) ExSender.sendBadRequest("Некорректная сумма для перевода");
        if (!clientRepository.existsClientById(senderId))
            ExSender.sendBadRequest("Отправитель с таким ИД не найден в БД");
        if (!clientRepository.existsClientById(recipientId))
            ExSender.sendBadRequest("Получатель с таким ИД не найден в БД");

        Client sender = clientRepository.getReferenceById(senderId);
        Client recipient = clientRepository.getReferenceById(recipientId);
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
