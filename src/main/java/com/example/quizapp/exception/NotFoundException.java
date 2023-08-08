package com.example.quizapp.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(MessageCode message) {
        super(message.getMessage());
    }
}
