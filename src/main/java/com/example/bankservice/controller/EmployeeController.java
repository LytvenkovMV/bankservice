package com.example.bankservice.controller;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Для сотрудников")
@RestController
@RequestMapping(path = "/bankservice/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Добавить клиента")
    @PostMapping
    public ResponseEntity<String> addClient(@RequestBody AddClientRequestDto addClientRequestDto) {
        employeeService.addClient(addClientRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Клиент добавлен");
    }
}
