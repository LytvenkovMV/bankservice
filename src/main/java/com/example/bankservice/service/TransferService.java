package com.example.bankservice.service;

import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.exception.BadRequestException;
import com.example.bankservice.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Slf4j
public class TransferService {

    private final ClientRepository clientRepository;

    public TransferService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public void transfer(TransferRequestDto transferRequestDto) {

        log.info("Получен запрос на перевод " + transferRequestDto.toString());

        Client sender;
        Client recipient;

        int senderId = transferRequestDto.getSenderId();
        int recipientId = transferRequestDto.getRecipientId();
        double amount = transferRequestDto.getAmount();


        ///////////////////////////////// ПРОВЕРИТЬ!!!!!!!!!!!!!!!!!!!!!!!!!
        if (senderId == recipientId) {
            log.info("ИД отправителя и получателя совпадают");
            throw new BadRequestException("ИД отправителя и получателя совпадают");
        }

        if (amount <= 0.00 || amount > 9999999999.99) {
            log.info("Некорректная сумма для перевода");
            throw new BadRequestException("Некорректная сумма для перевода");
        }


        ///////////////////////// НЕ РАБОТАЕТ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        ///////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
        try {
            sender = clientRepository.getById(senderId);
        } catch (EntityNotFoundException e) {
            log.info("Отправитель с таким ИД не найден в БД");
            throw new BadRequestException("Отправитель с таким ИД не найден в БД");
        }

        try {
            recipient = clientRepository.getById(recipientId);
        } catch (EntityNotFoundException e) {
            log.info("Получатель с таким ИД не найден в БД");
            throw new BadRequestException("Получатель с таким ИД не найден в БД");
        }








        Double senderCurrBalance = sender.getCurrBalance();
        Double recipientCurrBalance = recipient.getCurrBalance();

        if (senderCurrBalance >= amount) {
            sender.setCurrBalance(senderCurrBalance - amount);
            recipient.setCurrBalance(recipientCurrBalance + amount);
            clientRepository.save(sender);
            clientRepository.save(recipient);
        } else {
            log.info("Недостаточно средств для перевода");
            throw new BadRequestException("Недостаточно средств для перевода");
        }

        log.info("Перевод выполнен");
    }
}
