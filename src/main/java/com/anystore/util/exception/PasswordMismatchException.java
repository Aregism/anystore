package com.anystore.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PasswordMismatchException extends Exception{
    public PasswordMismatchException() {
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}
