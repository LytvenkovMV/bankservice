package com.example.bankservice.controller;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Для сотрудников")
@RestController
@RequestMapping(path = "/bankservice/employees")
public class EmployeeController {

    private final ClientService clientService;

    public EmployeeController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Добавить клиента")
    @PostMapping
    public void addClient(@RequestBody AddClientRequestDto addClientRequestDto) {
        clientService.addClient(addClientRequestDto);
    }
}
