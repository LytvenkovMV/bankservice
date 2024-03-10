package com.example.bankservice.controller;

import com.example.bankservice.dto.AddClientRequestDto;
import com.example.bankservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Для сотрудников")
@RestController
@RequestMapping(path = "/bankservice/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Приветствие")
    @GetMapping()
    public ResponseEntity<String> greeting() {
        return ResponseEntity.status(HttpStatus.OK).body("Приветствуем! Это сервис для сотрудников.");
    }

    @Operation(summary = "Добавить клиента")
    @PostMapping(path = "/client")
    public ResponseEntity<String> addClient(@RequestBody AddClientRequestDto addClientRequestDto) {
        employeeService.addClient(addClientRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Клиент добавлен");
    }
}
