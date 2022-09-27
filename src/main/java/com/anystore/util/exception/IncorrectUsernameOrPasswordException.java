package com.anystore.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class IncorrectUsernameOrPasswordException extends Exception {

    public IncorrectUsernameOrPasswordException() {
    }

    public IncorrectUsernameOrPasswordException(String message) {
        super(message);
    }
}
