package com.springframework.projectshoptoy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ListValidException extends  RuntimeException{
    public ListValidException() {
        super();
    }

    public ListValidException(String message) {
        super(message);
    }

    public ListValidException(String message, Throwable cause) {
        super(message, cause);
    }

}
