package com.anystore.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class TokenMismatchException extends Exception{
    public TokenMismatchException() {
    }

    public TokenMismatchException(String message) {
        super(message);
    }
}
