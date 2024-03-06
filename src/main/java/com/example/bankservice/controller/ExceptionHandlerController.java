package com.example.bankservice.controller;

import com.example.bankservice.dto.ErrorDto;
import com.example.bankservice.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ErrorDto> handleBadRequestEx(BadRequestException e) {
        ErrorDto errorDto = new ErrorDto(400, "Bad Request", e.getMessage());
        return ResponseEntity
                .status(400)
                .body(errorDto);
    }

//    @ExceptionHandler(value = {CurrencyServiceException.class})
//    public ResponseEntity<ErrorDto> handleCurrencyServiceEx() {
//        ErrorDto errorDto = new ErrorDto(503, "Service Unavailable"
//                , "Внешний сервис курсов валют не доступен. Проверьте адрес сервиса и ключ api в настройках приложения");
//        return ResponseEntity
//                .status(503)
//                .body(errorDto);
//    }

}
