package com.anystore.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TokenTimedOutException extends Exception{
    public TokenTimedOutException() {
    }

    public TokenTimedOutException(String message) {
        super(message);
    }
}
