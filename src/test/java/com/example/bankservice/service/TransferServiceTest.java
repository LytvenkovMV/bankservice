package com.example.bankservice.service;

import com.example.bankservice.configuration.jwt.JwtUtils;
import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.entity.Client;
import com.example.bankservice.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

class TransferServiceTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private TransferService transferService;

    @Test
    void transfer() {

        //given

        Double amount = 2.0;
        Double senderCurrBalance = 10.0;
        Double recipientCurrBalance = 10.0;
        String senderUserName = "ivan1";
        String recipientUserName = "ivan2";

        String headerAuth = "headerauth";

        TransferRequestDto transferRequestDto = new TransferRequestDto();
        transferRequestDto.setRecipientUsername(recipientUserName);
        transferRequestDto.setAmount(amount);

        Client sender = new Client();
        sender.setUsername(senderUserName);
        sender.setCurrBalance(senderCurrBalance);

        Client recipient = new Client();
        sender.setUsername(recipientUserName);
        sender.setCurrBalance(recipientCurrBalance);

        Mockito
                .when(jwtUtils.getUserNameFromJwtToken(jwtUtils.getJwt(any())))
                .thenReturn(senderUserName);

        Mockito
                .when(clientRepository.findClientByUsername(senderUserName))
                .thenReturn(sender);

        Mockito
                .when(clientRepository.findClientByUsername(recipientUserName))
                .thenReturn(recipient);

        //when

        transferService.transfer(headerAuth, transferRequestDto);


        //then




    }
}