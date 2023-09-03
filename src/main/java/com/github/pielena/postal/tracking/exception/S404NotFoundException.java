package com.github.pielena.postal.tracking.exception;

import org.springframework.http.HttpStatus;

public class S404NotFoundException extends RestException {


    public S404NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
