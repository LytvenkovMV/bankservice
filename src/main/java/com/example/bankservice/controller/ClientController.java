package com.example.bankservice.controller;

import com.example.bankservice.dto.ModifyEmailRequestDto;
import com.example.bankservice.dto.ModifyPhoneRequestDto;
import com.example.bankservice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Для клиентов")
@RestController
@RequestMapping(path = "/bankservice/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Добавить телефон")
    @PostMapping(path = "/phone")
    public void addPhone(@RequestBody ModifyPhoneRequestDto modifyPhoneRequestDto) {
        clientService.addPhone(modifyPhoneRequestDto);
    }

    @Operation(summary = "Удалить телефон")
    @DeleteMapping(path = "/phone")
    public void deletePhone(@RequestBody ModifyPhoneRequestDto modifyPhoneRequestDto) {
        clientService.deletePhone(modifyPhoneRequestDto);
    }

    @Operation(summary = "Добавить email")
    @PostMapping(path = "/email")
    public void addEmail(@RequestBody ModifyEmailRequestDto modifyEmailRequestDto) {
        clientService.addEmail(modifyEmailRequestDto);
    }

    @Operation(summary = "Удалить email")
    @DeleteMapping(path = "/email")
    public void deleteEmail(@RequestBody ModifyEmailRequestDto modifyEmailRequestDto) {
        clientService.deleteEmail(modifyEmailRequestDto);
    }
}
