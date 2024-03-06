package com.example.bankservice.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExSender {

    public static void sendBadRequest(String message) {
        log.info(message);
        throw new BadRequestException(message);
    }
}
