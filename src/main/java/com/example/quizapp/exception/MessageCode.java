package com.example.quizapp.exception;

import lombok.Getter;

@Getter
public enum MessageCode {
    NOT_FOUND_QUESTION ("Question not found."),
    NOT_FOUND_QUIZ ("Quiz not found"),
    NOT_FOUND_USER ("User not found"),
    NOT_FOUND_OPTION("Option not found"),
    NOT_FOUND_FEEDBACK("Feedback not found"),
    NOT_FOUND_POST("Post not found");

    private final String message;
    MessageCode(String message) {
        this.message = message;
    }

}
