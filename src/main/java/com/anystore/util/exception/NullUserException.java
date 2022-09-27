package com.anystore.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class NullUserException extends Exception{
    public NullUserException() {
    }

    public NullUserException(String message) {
        super(message);
    }
}
