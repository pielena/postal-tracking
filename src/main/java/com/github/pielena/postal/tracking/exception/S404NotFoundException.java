package com.github.pielena.postal.tracking.exception;

public class S404NotFoundException extends RestException {

    public S404NotFoundException(String message) {
        super("Necessary resource not found", message);
    }
}
