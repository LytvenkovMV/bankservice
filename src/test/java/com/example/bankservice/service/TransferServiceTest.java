package com.example.bankservice.service;

import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferServiceTest {

    private final TransferService transferService;
    private final ClientRepository clientRepository;

    public TransferServiceTest(TransferService transferService, ClientRepository clientRepository) {
        this.transferService = transferService;
        this.clientRepository = clientRepository;
    }

    @Test
    void transfer() {


        //given

        String amount = "2.0";

        String senderUserName = "ivan1";
        BigDecimal senderCurrBalance = new BigDecimal(10);

        String recipientUserName = "ivan2";
        BigDecimal recipientCurrBalance = new BigDecimal(10);


        TransferRequestDto transferRequestDto = new TransferRequestDto();
        transferRequestDto.setRecipientUsername(recipientUserName);
        transferRequestDto.setAmount(amount);

        Client sender = new Client();
        sender.setUsername(senderUserName);
        sender.setCurrBalance(senderCurrBalance);

        Client recipient = new Client();
        sender.setUsername(recipientUserName);
        sender.setCurrBalance(recipientCurrBalance);

        clientRepository.save(sender);
        clientRepository.save(recipient);


        //when

        transferService.transfer(senderUserName, transferRequestDto);


        //then

        sender = clientRepository.findClientByUsername(senderUserName);
        recipient = clientRepository.findClientByUsername(recipientUserName);


        assertEquals(senderCurrBalance.subtract(new BigDecimal(amount)), sender.getCurrBalance());
        assertEquals(recipientCurrBalance.add(new BigDecimal(amount)), recipient.getCurrBalance());
    }
}