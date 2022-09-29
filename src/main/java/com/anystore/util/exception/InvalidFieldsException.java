package com.anystore.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFieldsException extends Exception{
    public InvalidFieldsException() {
    }

    public InvalidFieldsException(String message) {
        super(message);
    }
}
