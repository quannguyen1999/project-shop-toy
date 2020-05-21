package com.springframework.projectshoptoy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Trả về status là 409 khi bị trùng id
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflixIdException extends RuntimeException{
    public ConflixIdException() {
        super();
    }

    public ConflixIdException(String message) {
        super(message);
    }

    public ConflixIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
