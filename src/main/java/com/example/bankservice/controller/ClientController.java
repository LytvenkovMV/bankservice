package com.example.bankservice.controller;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.dto.ModifyEmailRequestDto;
import com.example.bankservice.dto.ModifyPhoneRequestDto;
import com.example.bankservice.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(value = "Операции с клиентами")
@RestController
@RequestMapping(path = "/bankservice/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Добавить клиента")
    @PostMapping
    public void addClient(@RequestBody AddClientRequestDto addClientRequestDto) {
        clientService.addClient(addClientRequestDto);
    }

    @ApiOperation(value = "Добавить телефон клиента")
    @PostMapping(path = "/phone")
    public void addPhone(@RequestBody ModifyPhoneRequestDto modifyPhoneRequestDto) {
        clientService.addPhone(modifyPhoneRequestDto);
    }

    @ApiOperation(value = "Удалить телефон клиента")
    @DeleteMapping(path = "/phone")
    public void deletePhone(@RequestBody ModifyPhoneRequestDto modifyPhoneRequestDto) {
        clientService.deletePhone(modifyPhoneRequestDto);
    }

    @ApiOperation(value = "Добавить email клиента")
    @PostMapping(path = "/email")
    public void addEmail(@RequestBody ModifyEmailRequestDto modifyEmailRequestDto) {
        clientService.addEmail(modifyEmailRequestDto);
    }

    @ApiOperation(value = "Удалить email клиента")
    @DeleteMapping(path = "/email")
    public void deleteEmail(@RequestBody ModifyEmailRequestDto modifyEmailRequestDto) {
        clientService.deleteEmail(modifyEmailRequestDto);
    }
}
