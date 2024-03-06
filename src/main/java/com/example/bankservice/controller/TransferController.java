package com.example.bankservice.controller;

import com.example.bankservice.dto.TransferRequestDto;
import com.example.bankservice.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Операции с переводами")
@RestController
@RequestMapping(path = "/transfer")
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @ApiOperation(value = "Сделать перевод")
    @PostMapping
    public void addClient(@RequestBody TransferRequestDto transferRequestDto) {
        transferService.transfer(transferRequestDto);
    }
}
