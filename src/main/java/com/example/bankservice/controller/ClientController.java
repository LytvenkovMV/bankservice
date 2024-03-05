package com.example.bankservice.controller;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Операции с клиентами")
@RestController
@RequestMapping(path = "/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @ApiOperation(value = "Добавить клиента")
    @PostMapping
    public void addClient(@RequestBody AddClientRequestDto addClientRequestDto) {
        /////////////clientService.addClient(addClientRequestDto);
    }
}
