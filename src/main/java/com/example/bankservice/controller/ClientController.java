package com.example.bankservice.controller;

import com.example.bankservice.dto.ModifyEmailRequestDto;
import com.example.bankservice.dto.ModifyPhoneRequestDto;
import com.example.bankservice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addPhone(@RequestBody ModifyPhoneRequestDto modifyPhoneRequestDto) {
        clientService.addPhone(modifyPhoneRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Телефон добавлен");
    }

    @Operation(summary = "Удалить телефон")
    @DeleteMapping(path = "/phone")
    public ResponseEntity<String> deletePhone(@RequestBody ModifyPhoneRequestDto modifyPhoneRequestDto) {
        clientService.deletePhone(modifyPhoneRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Телефон удален");
    }

    @Operation(summary = "Добавить email")
    @PostMapping(path = "/email")
    public ResponseEntity<String> addEmail(@RequestBody ModifyEmailRequestDto modifyEmailRequestDto) {
        clientService.addEmail(modifyEmailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Email добавлен");
    }

    @Operation(summary = "Удалить email")
    @DeleteMapping(path = "/email")
    public ResponseEntity<String> deleteEmail(@RequestBody ModifyEmailRequestDto modifyEmailRequestDto) {
        clientService.deleteEmail(modifyEmailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Email удален");
    }
}
