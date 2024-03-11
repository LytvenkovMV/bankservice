package com.example.bankservice.controller;

import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.service.AuthService;
import com.example.bankservice.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Переводы")
@RestController
@RequestMapping(path = "/bankservice/transfer")
public class TransferController {

    private final AuthService authService;
    private final TransferService transferService;

    public TransferController(AuthService authService, TransferService transferService) {
        this.authService = authService;
        this.transferService = transferService;
    }

    @Operation(summary = "Приветствие")
    @GetMapping()
    public ResponseEntity<String> greeting(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth) {
        return ResponseEntity.status(HttpStatus.OK).body(transferService.greeting(authService.getUsername(headerAuth)));
    }

    @Operation(summary = "Сделать перевод")
    @PostMapping
    public ResponseEntity<String> addClient(@RequestHeader(HttpHeaders.AUTHORIZATION) String headerAuth,
                                            @RequestBody TransferRequestDto transferRequestDto) {
        transferService.transfer(authService.getUsername(headerAuth), transferRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Перевод выполнен");
    }
}
