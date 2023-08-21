package com.example.quizapp.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotFoundException extends RuntimeException{
    public NotFoundException(MessageCode message) {
        super(message.getMessage());
        log.error(message.getMessage());
    }
}
