package com.example.bankservice.controller;

import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Переводы")
@RestController
@RequestMapping(path = "/bankservice/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Operation(summary = "Сделать перевод")
    @PostMapping
    public ResponseEntity<String> addClient(@RequestBody TransferRequestDto transferRequestDto) {
        transferService.transfer(transferRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("Перевод выполнен");
    }
}
